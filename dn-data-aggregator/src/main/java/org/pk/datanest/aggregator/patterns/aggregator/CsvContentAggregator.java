package org.pk.datanest.aggregator.patterns.aggregator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Service("csvContentAggregator")
public class CsvContentAggregator implements Aggregator {
    Logger logger = LoggerFactory.getLogger(CsvContentAggregator.class);

    @Override
    public void aggregate(String content, String specification) {
        logger.info("aggregate: start");
        JSONObject jsonObject = new JSONObject(specification);
        JSONArray jsonArray = jsonObject.getJSONArray("columns");
        List<String> specificationColumns = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject columnDetails = jsonArray.getJSONObject(i);
            String column = columnDetails.getString("column");
            specificationColumns.add(column);
        }
        logger.info("aggregate: specificationColumns: {}", specificationColumns);
        extractCSVColumns(content, specificationColumns);
        logger.info("aggregate: end");
    }

    private void extractCSVColumns(String content, List<String> specificationColumns) {
        StringTokenizer csvTokenizer = new StringTokenizer(content, ",");
        int noOfColumns = csvTokenizer.countTokens();
        logger.info("extractCSVColumns: noOfColumns: {}", noOfColumns);

    }
}
