package org.pk.datanest.strategy.executor.patterns.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.pk.datanest.strategy.executor.constant.Constant;
import org.pk.datanest.strategy.executor.exception.InvalidSpecificationException;
import org.pk.datanest.strategy.executor.service.FileService;
import org.pk.datanest.strategy.executor.specification.Column;
import org.pk.datanest.strategy.executor.specification.JsonSpecification;
import org.pk.datanest.strategy.executor.specification.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("csvContentStrategy")
public class CsvContentStrategy implements Strategy {

    Logger logger = LoggerFactory.getLogger(CsvContentStrategy.class);

    @Autowired
    FileService fileService;

    private static JsonSpecification getJsonSpecification(String jsonSpecificationString) {
        try {
            ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();
            return objectMapper.readValue(jsonSpecificationString, JsonSpecification.class);

        } catch (Exception e) {
            throw new InvalidSpecificationException(e.getMessage());
        }
    }

    /**
     * @param specificationColumns are the columns list extracted from .json specification as per the given order
     * @param headerRow            row represents csv file columns list with csv delimiter
     * @return List<Integer> represents column name index in header row will be useful to extract data
     */
    private static List<Integer> getSpecificationColumnsIndex(List<String> specificationColumns, String headerRow) {
        List<Integer> specificationColumnsIndex = new ArrayList<>(specificationColumns.size());
        List<String> headers = Arrays.stream(headerRow.split(Constant.CSV_DELIMITER)).toList();
        for (String csvColumn : specificationColumns) {
            int index = headers.indexOf(csvColumn);
            if (index == -1) {
                throw new InvalidSpecificationException("'" + csvColumn + "' specification column does not match with the content header " + headers);
            }
            specificationColumnsIndex.add(index);
        }
        return specificationColumnsIndex;
    }

    private StringBuffer applyStrategy(String csvContent, JsonSpecification jsonSpecification) {
        String[] lines = csvContent.split("\r\n");
        List<Integer> specificationColumnsIndex = getSpecificationColumnsIndex(jsonSpecification.getColumns().stream().map(Column::getColumn).toList(), lines[0]);

        StringBuffer buffer = new StringBuffer();
        for (int i = 1; i < lines.length; i++) { //skipping first row as its headers row
            String[] row = lines[i].split(Constant.CSV_DELIMITER);
            for (int columnsIndex : specificationColumnsIndex) {
                Column column = jsonSpecification.getColumns().get(columnsIndex);
                List<Rule> rules = column.getRules();
                String columnValue = row[columnsIndex];
                logger.info("applyStrategy: column: {} noOfRules: {}", column.getColumn(), rules.size());
                for (Rule rule : rules) {
                    logger.info("applyStrategy: column: {} ruleName: {}", column.getColumn(), rule.getName());

                    try (Context context = Context.create()) {
                        Value value = context.eval("js", rule.getRuleDef());
                        Value execute = value.execute(columnValue);
                        columnValue = String.valueOf(execute);
                        logger.info("applyStrategy: ruleName: {} applied {}", rule.getName(), columnValue);
                    }
                }
                buffer.append(columnValue);
                buffer.append(",");
            }
            buffer.deleteCharAt(buffer.length() - 1);
            buffer.append("\r\n");
        }
        return buffer;
    }

    @Override
    public void execute(StrategyContext strategyContext) throws IOException {
        if (strategyContext instanceof CsvStrategyContext csvStrategyContext) {

            String csvContent = fileService.loadAsResource(csvStrategyContext.getCsvFileName()).getContentAsString(Charset.defaultCharset());
            String jsonSpecificationContent = fileService.loadAsResource(csvStrategyContext.getJsonSpecificationFileName()).getContentAsString(Charset.defaultCharset());

            JsonSpecification jsonSpecification = getJsonSpecification(jsonSpecificationContent);

            StringBuffer buffer = applyStrategy(csvContent, jsonSpecification);
            buffer.insert(0, String.join(",", jsonSpecification.getColumns().stream().map(Column::getColumn).toList()) + "\r\n");
            fileService.save("final", "1_strategy_applied.csv", buffer.toString());
            logger.info("execute: data:\n{}", buffer);
        }
    }
}
