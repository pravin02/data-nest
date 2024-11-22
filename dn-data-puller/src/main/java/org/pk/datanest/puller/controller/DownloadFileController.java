package org.pk.datanest.puller.controller;

import org.pk.datanest.puller.constant.Constant;
import org.pk.datanest.puller.patterns.strategy.DataPollFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final DataPollFacade dataPollFacade;
    Logger logger = LoggerFactory.getLogger(DownloadFileController.class);

    public DownloadFileController(DataPollFacade dataPollFacade) {
        this.dataPollFacade = dataPollFacade;
    }

    @GetMapping("/{clientId}/{filename:.+}")
    public ResponseEntity<?> serveFile(@PathVariable int clientId, @PathVariable String filename) {
        logger.info("serveFile: dataPoll request received for clientId {}", clientId);
        Map<String, String> map = new HashMap<>(1);
        map.put(Constant.FILE_NAME, filename);
        map.put(Constant.CLIENT_ID, String.valueOf(clientId));
        dataPollFacade.poll(clientId, map);
        return ResponseEntity.ok().body("File downloaded Successfully.");
    }
}
