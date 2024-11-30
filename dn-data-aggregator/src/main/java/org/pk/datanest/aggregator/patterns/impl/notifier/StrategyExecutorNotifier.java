package org.pk.datanest.aggregator.patterns.impl.notifier;

import org.pk.datanest.aggregator.patterns.notifier.ResourceNotifier;
import org.pk.datanest.aggregator.patterns.notifier.exception.NotificationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class StrategyExecutorNotifier implements ResourceNotifier<String> {

    Logger logger = LoggerFactory.getLogger(StrategyExecutorNotifier.class);

    @Autowired
    RestClient restClient;

    @Override
    public void notify(String clientId) throws NotificationFailedException {
        try {
            logger.info("notify: notifying to strategy execution service");
            ResponseEntity<String> response = restClient.get().uri("strategy/execute/" + clientId)
                    .retrieve().toEntity(String.class);
            logger.info("notify: response: {}", response);
        } catch (Exception e) {
            logger.error("notify: failed to notify executor strategy service", e);
            throw new NotificationFailedException(e.getMessage());
        }
    }
}
