package org.pk.datanest.puller.controller;


import org.pk.datanest.commons.constant.Constant;
import org.pk.datanest.puller.patterns.strategy.pull.impl.DataPullFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class DownloadFileController {

    Logger logger = LoggerFactory.getLogger(DownloadFileController.class);

    @Autowired
    private DataPullFacade dataPullFacade;

    @GetMapping("/{clientId}/{filename:.+}")
    public ResponseEntity<?> pullFile(@PathVariable int clientId, @PathVariable String filename) {
        logger.info("pullFile: dataPoll request received for clientId {}", clientId);
        Map<String, String> map = new HashMap<>(2);
        map.put(Constant.FILE_NAME, filename);
        map.put(Constant.CLIENT_ID, String.valueOf(clientId));
        dataPullFacade.pull(map);
        return ResponseEntity.ok().body("File downloaded Successfully.");
    }
}
