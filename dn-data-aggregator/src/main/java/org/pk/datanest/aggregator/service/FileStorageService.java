package org.pk.datanest.aggregator.service;

import jakarta.annotation.PostConstruct;
import org.pk.datanest.aggregator.exception.StorageFileNotFoundException;
import org.pk.datanest.aggregator.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService implements FileService {

    Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Value("${dataset.location}")
    private String dataSetFileLocation;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        rootLocation = Paths.get(dataSetFileLocation);
    }


    public void save(String fileName, String content) {
        try {
            logger.info("save: location: {}", dataSetFileLocation + fileName);
            FileUtils.saveFile(dataSetFileLocation + fileName, content);
        } catch (IOException e) {
            logger.error("saveFile: failed to save file {} with exception {}", fileName, e.getMessage());
        }
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }


    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }
}
