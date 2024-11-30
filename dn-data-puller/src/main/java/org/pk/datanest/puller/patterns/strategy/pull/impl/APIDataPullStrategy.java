package org.pk.datanest.puller.patterns.strategy.pull.impl;

import org.pk.datanest.commons.constant.Constant;
import org.pk.datanest.commons.patterns.notifier.ResourceNotifier;
import org.pk.datanest.commons.patterns.notifier.StatusNotifier;
import org.pk.datanest.commons.patterns.notifier.exception.NotificationFailedException;
import org.pk.datanest.commons.patterns.notifier.model.Status;
import org.pk.datanest.commons.patterns.strategy.PullStrategy;
import org.pk.datanest.commons.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.Map;

@Service("apiDataPullStrategy")
@Primary
public class APIDataPullStrategy extends PullStrategy<Map<String, String>, Object> implements StatusNotifier {

    Logger logger = LoggerFactory.getLogger(APIDataPullStrategy.class);

    @Autowired
    FileService fileService;

    @Autowired
    @Qualifier("datasetHost")
    RestClient restClient;

    @Autowired
    ResourceNotifier<String> notifier;

    private Map<String, String> dataMap;

    @Override
    public Object execute(Map<String, String> dataMap) {
        notifyStatus(Status.STARTED, null);
        this.dataMap = dataMap;
        logger.info("execute: dataMap: {}", dataMap);
        String fileName = dataMap.get(Constant.FILE_NAME) + ".csv";
        try {
            String csvContent = pollFile(fileName);
            saveFile(fileName, csvContent);
        } catch (IOException e) {
            notifyStatus(Status.FAILED, new RuntimeException("Failed to pull or save data file"));
            return null;
        }

        notifyStatus(Status.IN_PROGRESS, null);

        fileName = getSpecificationFileName(dataMap.get(Constant.FILE_NAME));
        try {
            String jsonContent = pollFile(fileName);
            saveFile(fileName, jsonContent);
        } catch (IOException e) {
            notifyStatus(Status.FAILED, new RuntimeException("Failed to pull or save specification file"));
            return null;
        }
        notifyStatus(Status.COMPLETED, null);
        return null;
    }

    @Override
    public void notifyStatus(Status status, Throwable throwable) {
        switch (status) {
            case STARTED -> logger.info("notifyStatus: {}: API based polling started", status);
            case IN_PROGRESS -> logger.info("notifyStatus: {}: API based polling in progress", status);
            case COMPLETED -> {
                try {
                    logger.info("notifyStatus: {}: API based data polled successfully.", status);
                    notifier.notify(dataMap.get(Constant.CLIENT_ID));
                } catch (NotificationFailedException e) {
                    logger.error("notifyStatus: API based polling strategy failed notify aggregator", e);
                }
            }
            case FAILED -> logger.error("notifyStatus: {}: API based polling failed", status, throwable);
        }
    }

    private String getSpecificationFileName(String fileName) {
        return fileName + ".json";
    }

    protected String pollFile(String fileName) {
        logger.info("pollFile: fileName: {}", fileName);
        return restClient.get().uri("files/" + fileName).retrieve().body(String.class);
    }


    protected void saveFile(String fileName, String content) throws IOException {
        logger.info("saveFile: fileName: {}", fileName);
        fileService.save(fileName, content);
    }

}
