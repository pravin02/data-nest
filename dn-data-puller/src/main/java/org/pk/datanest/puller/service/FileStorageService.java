package org.pk.datanest.puller.service;

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

    public void save(String fileName, String content) {
        try {
            logger.info("save: location: {}", dataSetFileLocation + fileName);
            FileUtils.saveFile(dataSetFileLocation + fileName, content);
        } catch (IOException e) {
            logger.error("saveFile: failed to save file {} with exception {}", fileName, e.getMessage());
        }
    }
}
