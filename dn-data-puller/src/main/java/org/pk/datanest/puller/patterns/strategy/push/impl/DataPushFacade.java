package org.pk.datanest.puller.patterns.strategy.push.impl;

import org.pk.datanest.commons.constant.ConnectionMode;
import org.pk.datanest.commons.constant.Constant;
import org.pk.datanest.commons.patterns.strategy.PushStrategy;
import org.pk.datanest.puller.model.Client;
import org.pk.datanest.puller.service.StaticDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class DataPushFacade {

    Logger logger = LoggerFactory.getLogger(DataPushFacade.class);

    @Autowired
    @Qualifier("apiDataPushStrategy")
    PushStrategy<Map<String, String>, Object> apiDataPushStrategy;


    private static Optional<Client> getClient(String clientId) {
        Predicate<Client> IS_CLIENT_MATCH_PREDICATE = (Client client) -> clientId.equals(client.getId());
        return StaticDataService.getClients().stream().filter(IS_CLIENT_MATCH_PREDICATE).findFirst();
    }

    public void push(Map<String, String> map) {
        Optional<Client> c = getClient(map.get(Constant.CLIENT_ID));
        logger.info("poll: client: {}", c);
        if (c.isEmpty()) {
            throw new RuntimeException("Client details not found");
        }
        switch (c.get().getConnectionMode()) { // get config from db based on that execute strategy
            case API -> apiDataPushStrategy.execute(map);
            case WEB_SOCKET -> throw new RuntimeException("Strategy yet to implement for " + ConnectionMode.WEB_SOCKET);
            case SFT -> throw new RuntimeException("Strategy yet to implement for " + ConnectionMode.SFT);
            case FILE_SYSTEM ->
                    throw new RuntimeException("Strategy yet to implement for " + ConnectionMode.FILE_SYSTEM);
            case null ->
                    throw new RuntimeException("For Client " + c.get().getId() + " don't have any strategy to poll data");
        }
    }
}
