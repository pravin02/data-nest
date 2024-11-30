package org.pk.datanest.puller.patterns.strategy.impl;

import org.pk.datanest.puller.constant.Constant;
import org.pk.datanest.puller.patterns.notifier.Notifier;
import org.pk.datanest.puller.patterns.strategy.Strategy;
import org.pk.datanest.puller.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public abstract class DataPollingStrategy implements Strategy {

    Logger logger = LoggerFactory.getLogger(DataPollingStrategy.class);

    @Autowired
    FileService fileService;

    @Autowired
    Notifier notifier;

    abstract public Object execute(Map<String, String> dataMap);

    @Override
    public void executionCompleted(Map<String, String> dataMap) {
        String clientId = dataMap.get(Constant.CLIENT_ID);
        logger.info("executionCompleted: Execution completed for client: {}", clientId);
        notifier.notify(clientId);
    }

    protected String getSpecificationFileName(String fileName) {
        return fileName + ".json";
    }

    protected void saveFile(String fileName, String content) {
        logger.info("saveFile: fileName: {}", fileName);
        fileService.save(fileName, content);
    }
}
