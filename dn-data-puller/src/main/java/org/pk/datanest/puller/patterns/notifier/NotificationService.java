package org.pk.datanest.puller.patterns.notifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NotificationService implements Notifier {

    Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    @Qualifier("aggregatorHost")
    RestClient restClient;

    @Override
    public void sendNotification(String clientId) {
        String response = restClient.get().uri("notification/" + clientId).retrieve().body(String.class);
        logger.info("sendNotification: response: {}", response);
        logger.info("sendNotification: notification sent successfully.");
    }
}
