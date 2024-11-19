package org.pk.datanest.dataset.controller;

import org.pk.datanest.dataset.exception.StorageFileNotFoundException;
import org.pk.datanest.dataset.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileServerController {

    Logger logger = LoggerFactory.getLogger(FileServerController.class);

    private final StorageService storageService;

    public FileServerController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public String listUploadedFiles() {
        return storageService.loadAll().map(path -> path.getFileName().toString()).collect(Collectors.joining(","));
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<?> serveFile(@PathVariable String filename) throws IOException {
        logger.info("serveFile: request received to download file: {}", filename);
        Resource file = storageService.loadAsResource(filename);
        if (file == null) {
            throw new StorageFileNotFoundException(filename + " not found");
        }
        return ResponseEntity.ok(file.getContentAsString(Charset.defaultCharset()));
    }
}
