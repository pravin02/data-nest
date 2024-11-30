package org.pk.datanest.aggregator.service;

import org.pk.datanest.aggregator.constant.FileType;
import org.pk.datanest.aggregator.patterns.aggregator.Aggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
public class AggregatorFacade {
    Logger logger = LoggerFactory.getLogger(AggregatorFacade.class);

    @Autowired
    @Qualifier("csvContentAggregator")
    Aggregator<String, String> csvContentAggregator;

    @Autowired
    FileService fileService;

    public void processFile(String dataFileName, String specificationFileName) throws IOException {
        logger.info("processFile: dataFileName: {}", dataFileName);
        logger.info("processFile: specificationFileName: {}", specificationFileName);

        String content = fileService.loadAsResource(dataFileName).getContentAsString(Charset.defaultCharset());
        String specificationContent = fileService.loadAsResource(specificationFileName).getContentAsString(Charset.defaultCharset());
        String dataFileExtension = dataFileName.substring(dataFileName.indexOf(".") + 1);
        logger.info("processFile: dataFileExtension: {}", dataFileExtension);

        switch (FileType.getFileType(dataFileExtension)) {
            case CSV -> {
                csvContentAggregator.aggregate(content, specificationContent);
            }
            case JSON -> {
            }
            case HTML -> {
            }
            case XLS -> {
            }
            case null -> {
            }
        }
    }
}
