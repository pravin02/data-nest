package org.pk.datanest.strategy.executor.controller;

import org.pk.datanest.strategy.executor.patterns.strategy.StrategyExecutorFacade;
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
@RequestMapping("/strategy")
public class StrategyExecutorNotificationController {

    Logger logger = LoggerFactory.getLogger(StrategyExecutorNotificationController.class);

    @Autowired
    StrategyExecutorFacade strategyExecutorFacade;

    @GetMapping("/execute/{clientId}")
    public ResponseEntity<?> executeStrategy(@PathVariable int clientId) throws IOException {
        logger.info("executeStrategy: notification received from client id {}", clientId);
        strategyExecutorFacade.execute(clientId);
        return ResponseEntity.ok().body("Notification received for Client Id " + clientId + " for strategy execution");
    }
}
