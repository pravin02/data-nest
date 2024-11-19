package org.pk.datanest.puller.patterns.strategy.impl;

import org.pk.datanest.puller.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component("webSocketDataPollingStrategy")
public class WebSocketDataPollingStrategy extends DataPollingStrategy {

    Logger logger = LoggerFactory.getLogger(WebSocketDataPollingStrategy.class);

    RestClient restClient;


    public WebSocketDataPollingStrategy(@Autowired @Qualifier("datasetHost") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Object executeStrategy(Map<String, String> dataMap) {
        logger.info("executeStrategy: dataMap: {}", dataMap);
        String fileName = dataMap.get(Constant.FILE_NAME) + ".csv";
        String csvContent = (String) downloadFile(fileName, String.class);
        saveFile("SOCKET_" + fileName, csvContent);

        fileName = getSpecificationFileName(dataMap.get(Constant.FILE_NAME));
        String jsonContent = (String) downloadFile(fileName, String.class);
        saveFile("SOCKET_" + fileName, jsonContent);
        executionCompleted(dataMap);
        return null;
    }

    public Object downloadFile(String fileName, Class<?> clazz) {
        logger.info("downloadFile: fileName: {}", fileName);
        return restClient
                .get()
                .uri("files/" + fileName)
                .retrieve()
                .body(clazz);
    }

}
