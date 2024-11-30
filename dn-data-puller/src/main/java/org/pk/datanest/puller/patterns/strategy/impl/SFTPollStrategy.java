package org.pk.datanest.puller.patterns.strategy.impl;


import org.pk.datanest.commons.constant.Constant;
import org.pk.datanest.commons.patterns.notifier.ResourceNotifier;
import org.pk.datanest.commons.patterns.notifier.StatusNotifier;
import org.pk.datanest.commons.patterns.notifier.exception.NotificationFailedException;
import org.pk.datanest.commons.patterns.notifier.model.Status;
import org.pk.datanest.commons.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service("sftDataPollingStrategy")
public class SFTPollStrategy extends PollStrategy<Map<String, String>, Object> implements StatusNotifier {

    Logger logger = LoggerFactory.getLogger(SFTPollStrategy.class);

    @Autowired
    FileService fileService;

    @Autowired
    ResourceNotifier<String> notifier;

    private Map<String, String> dataMap;

    @Override
    public Object execute(Map<String, String> dataMap) {
        notifyStatus(Status.STARTED, null);
        this.dataMap = dataMap;
        logger.info("execute: dataMap: {}", dataMap);
        String fileName = dataMap.get(Constant.FILE_NAME) + ".csv";
        String csvContent = pollFile(fileName);
        try {
            saveFile("SFT_" + fileName, csvContent);
        } catch (IOException e) {
            notifyStatus(Status.FAILED, e);
            return null;
        }
        notifyStatus(Status.IN_PROGRESS, null);

        fileName = getSpecificationFileName(dataMap.get(Constant.FILE_NAME));
        String jsonContent = pollFile(fileName);
        try {
            saveFile("SFT_" + fileName, jsonContent);
        } catch (IOException e) {
            notifyStatus(Status.FAILED, e);
            return null;
        }
        notifyStatus(Status.COMPLETED, null);
        return null;
    }

    @Override
    public void notifyStatus(Status status, Throwable throwable) {
        switch (status) {
            case STARTED -> logger.info("notifyStatus: {} SFT based polling started", status);
            case IN_PROGRESS -> logger.info("notifyStatus: {} SFT based polling in progress", status);
            case COMPLETED -> {
                try {
                    notifier.notify(dataMap.get(Constant.CLIENT_ID));
                } catch (NotificationFailedException e) {
                    logger.error("notifyStatus: SFT based failed notify aggregator", e);
                }
            }
            case FAILED -> logger.info("notifyStatus: {} SFT based polling failed", status);
        }
    }

    private String getSpecificationFileName(String fileName) {
        return fileName + ".json";
    }

    protected String pollFile(String fileName) {
        logger.info("pollFile: fileName: {}", fileName);
        return "SFT";
    }


    protected void saveFile(String fileName, String content) throws IOException {
        logger.info("saveFile: fileName: {}", fileName);
        fileService.save(fileName, content);
    }
}
