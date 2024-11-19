package org.pk.datanest.puller.patterns.strategy.impl;

import org.pk.datanest.puller.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("sftDataPollingStrategy")
public class SFTDataPollingStrategy extends DataPollingStrategy {

    Logger logger = LoggerFactory.getLogger(SFTDataPollingStrategy.class);

    public SFTDataPollingStrategy() {
        super();
    }

    @Override
    public Object executeStrategy(Map<String, String> dataMap) {
        logger.info("executeStrategy: dataMap: {}", dataMap);
        String fileName = dataMap.get(Constant.FILE_NAME) + ".csv";
        String csvContent = (String) downloadFile(fileName, String.class);
        saveFile("SFT_" + fileName, csvContent);

        fileName = getSpecificationFileName(dataMap.get(Constant.FILE_NAME));
        String jsonContent = (String) downloadFile(fileName, String.class);
        saveFile("SFT_" + fileName, jsonContent);
        executionCompleted(dataMap);
        return null;
    }

    public Object downloadFile(String fileName, Class<?> clazz) {
        logger.info("downloadFile: fileName: {}", fileName);
        return "SFT";
    }
}
