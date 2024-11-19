package org.pk.datanest.aggregator.patterns.aggregator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pk.datanest.aggregator.constant.Constant;
import org.pk.datanest.aggregator.exception.InvalidSpecificationException;
import org.pk.datanest.aggregator.specification.Column;
import org.pk.datanest.aggregator.specification.JsonSpecification;
import org.pk.datanest.aggregator.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("csvContentAggregator")
public class CsvContentAggregator implements Aggregator {

    Logger logger = LoggerFactory.getLogger(CsvContentAggregator.class);

    @Value("${dataset.location}")
    private String datasetLocation;

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

    /**
     * @param lines                     array of csv lines
     * @param specificationColumnsIndex index as per json specification
     * @return data extracted based in json specification column order with csv delimister
     */
    private static StringBuffer getCsvDataBuffer(String[] lines, List<Integer> specificationColumnsIndex) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 1; i < lines.length; i++) { //skipping first row as its headers row
            String[] row = lines[i].split(Constant.CSV_DELIMITER);
            for (int columnsIndex : specificationColumnsIndex) {
                String columnValue = row[columnsIndex];
                buffer.append(columnValue);
                buffer.append(",");
            }
            buffer.deleteCharAt(buffer.length() - 1);
            buffer.append("\r\n");
        }
        return buffer;
    }

    /**
     * @param jsonSpecificationString json specification string
     * @return list columns provided in specification file
     */
    private static List<String> getSpecificationColumns(String jsonSpecificationString) {
        try {
            ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();
            JsonSpecification jsonSpecification = objectMapper.readValue(jsonSpecificationString, JsonSpecification.class);
            return jsonSpecification.getColumns().stream().map(Column::getColumn).toList();
        } catch (Exception e) {
            throw new InvalidSpecificationException(e.getMessage());
        }
    }

    /**
     * @param csvContent        content in csv format
     * @param jsonSpecification json specification to aggregate data from csv content
     * @throws IOException if fails to save aggregate data
     */
    @Override
    public void aggregate(String csvContent, String jsonSpecification) throws IOException {
        logger.info("aggregate: start");
        List<String> specificationColumns = getSpecificationColumns(jsonSpecification);
        logger.info("aggregate: specificationColumns: {}", specificationColumns);
        String aggregatedData = extractCsvData(csvContent, specificationColumns);
        logger.info("aggregate: data:\n{}", aggregatedData);
        FileUtils.saveFile(datasetLocation + "_aggregated.csv", aggregatedData);
        logger.info("aggregate: end");
    }


    /**
     * @param csvContent           csv content
     * @param specificationColumns columns as per provided in json specification
     * @return final content of in csv format only based in specification columns
     */
    private String extractCsvData(String csvContent, List<String> specificationColumns) {
        String[] lines = csvContent.split("\r\n");
        if (lines.length == 0) {
            throw new RuntimeException("No Data to extract fro CSV content");
        }
        List<Integer> specificationColumnsIndex = getSpecificationColumnsIndex(specificationColumns, lines[0]); // extracting header index
        logger.info("extractCsvData: specificationColumnsIndex: {}", specificationColumnsIndex);
        StringBuffer buffer = getCsvDataBuffer(lines, specificationColumnsIndex);// extracting row data based on specification column
        buffer.insert(0, String.join(",", specificationColumns) + "\r\n"); // pre fixing header columns in data string
        return buffer.toString();
    }
}
