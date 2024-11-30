package org.pk.datanest.puller.patterns.strategy.pull.impl;

import org.pk.datanest.commons.constant.ConnectionMode;
import org.pk.datanest.commons.constant.Constant;
import org.pk.datanest.commons.patterns.strategy.PullStrategy;
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
public class DataPullFacade {

    Logger logger = LoggerFactory.getLogger(DataPullFacade.class);

    @Autowired
    @Qualifier("apiDataPullStrategy")
    PullStrategy<Map<String, String>, Object> apiDataPullStrategy;

    @Autowired
    @Qualifier("sftPullStrategy")
    PullStrategy<Map<String, String>, Object> sftPullStrategy;

    @Autowired
    @Qualifier("webSocketPullStrategy")
    PullStrategy<Map<String, String>, Object> webSocketPullStrategy;

    private static Optional<Client> getClient(String clientId) {
        Predicate<Client> IS_CLIENT_MATCH_PREDICATE = (Client client) -> clientId.equals(client.getId());
        return StaticDataService.getClients().stream().filter(IS_CLIENT_MATCH_PREDICATE).findFirst();
    }

    public void pull(Map<String, String> map) {
        Optional<Client> c = getClient(map.get(Constant.CLIENT_ID));
        logger.info("poll: client: {}", c);
        if (c.isEmpty()) {
            throw new RuntimeException("Client details not found");
        }
        switch (c.get().getConnectionMode()) { // get config from db based on that execute strategy
            case API -> apiDataPullStrategy.execute(map);
            case WEB_SOCKET -> webSocketPullStrategy.execute(map);
            case SFT -> sftPullStrategy.execute(map);
            case FILE_SYSTEM ->
                    throw new RuntimeException("Strategy yet to implement for " + ConnectionMode.FILE_SYSTEM);
            case null ->
                    throw new RuntimeException("For Client " + c.get().getId() + " don't have any strategy to poll data");
        }
    }
}
