package org.pk.datanest.puller.service;

import org.pk.datanest.commons.patterns.notifier.ResourceNotifier;
import org.pk.datanest.commons.patterns.notifier.exception.NotificationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Primary
@Service
public class AggregatorNotificationService implements ResourceNotifier<String> {

    Logger logger = LoggerFactory.getLogger(AggregatorNotificationService.class);

    @Autowired
    @Qualifier("aggregatorHost")
    RestClient restClient;

    @Override
    public void notify(String clientId) throws NotificationFailedException {
        try {
            String response = restClient.get().uri("notification/" + clientId)
                    .retrieve()
                    .body(String.class);
            logger.info("notify: response: {}", response);
            logger.info("notify: notification sent successfully for client {}", clientId);
        } catch (Exception e) {
            throw new NotificationFailedException(e.getMessage());
        }
    }
}
