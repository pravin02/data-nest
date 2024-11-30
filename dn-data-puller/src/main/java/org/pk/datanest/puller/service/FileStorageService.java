package org.pk.datanest.puller.service;

import org.pk.datanest.commons.service.FileService;
import org.pk.datanest.puller.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FileStorageService implements FileService {

    Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Value("${dataset.location}")
    private String dataSetFileLocation;

    @Override
    public void save(String fileName, String content) throws IOException {
        logger.info("save: location: {}", dataSetFileLocation + fileName);
        FileUtils.saveFile(dataSetFileLocation + fileName, content);
    }

    @Override
    public void save(String location, String fileName, String content) throws IOException {
        logger.info("save: location: {}, fileName: {}", location, fileName);
        FileUtils.saveFile(location + fileName, content);
    }
}
