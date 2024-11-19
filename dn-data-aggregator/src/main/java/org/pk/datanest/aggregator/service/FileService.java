package org.pk.datanest.aggregator.service;

import org.springframework.core.io.Resource;

public interface FileService {

    void save(String fileName, String content);

    Resource loadAsResource(String fileName);
}
