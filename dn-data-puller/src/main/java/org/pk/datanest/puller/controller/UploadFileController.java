package org.pk.datanest.puller.controller;


import org.pk.datanest.commons.constant.Constant;
import org.pk.datanest.puller.patterns.strategy.push.impl.DataPushFacade;
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
public class UploadFileController {

    Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    @Autowired
    private DataPushFacade dataPushFacade;

    @GetMapping("upload/{clientId}/{filename:.+}")
    public ResponseEntity<?> pushFile(@PathVariable int clientId, @PathVariable String filename) {
        logger.info("pushFile: dataPush request received for clientId {}", clientId);
        Map<String, String> map = new HashMap<>(1);
        map.put(Constant.FILE_NAME, filename);
        map.put(Constant.CLIENT_ID, String.valueOf(clientId));
        dataPushFacade.push(map);
        return ResponseEntity.ok().body("File uploaded Successfully.");
    }
}
