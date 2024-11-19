package org.pk.datanest.aggregator.controller;

import org.pk.datanest.aggregator.service.AggregatorFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private AggregatorFacade aggregatorFacade;


    @GetMapping("/{clientId}")
    public ResponseEntity<?> serveFile(@PathVariable int clientId) throws IOException {
        // read .csv/.json/.xls/.html and .json files and filter data based in specification.json
        aggregatorFacade.processFile(clientId + ".csv", clientId + ".json");
        return ResponseEntity.ok().body("Notification received for Client Id " + clientId + " or aggregation");
    }
}
