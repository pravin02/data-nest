package org.pk.datanest.puller.patterns.notifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Primary
@Service
public class AggregatorNotificationService implements Notifier<String> {

    Logger logger = LoggerFactory.getLogger(AggregatorNotificationService.class);

    @Autowired
    @Qualifier("aggregatorHost")
    RestClient restClient;

    @Override
    public void notify(String clientId) {
        String response = restClient.get().uri("notification/" + clientId)
                .retrieve()
                .body(String.class);
        logger.info("sendNotification: response: {}", response);
        logger.info("sendNotification: notification sent successfully for client {}", clientId);
    }
}
