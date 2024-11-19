package org.pk.datanest.puller.patterns.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DataPollFacade {

    @Autowired
    @Qualifier("apiDataPollingStrategy")
    Strategy apiDataPollingStrategy;

    @Autowired
    @Qualifier("webSocketDataPollingStrategy")
    Strategy webSocketDataPollingStrategy;

    @Autowired
    @Qualifier("sftDataPollingStrategy")
    Strategy sftDataPollingStrategy;

    public void poll(int clientId, Map<String, String> map) {
        switch (clientId) { // get config from db based on that execute strategy
            case 1 -> apiDataPollingStrategy.execute(map);
            case 2 -> webSocketDataPollingStrategy.execute(map);
            case 3 -> sftDataPollingStrategy.execute(map);
            default -> throw new RuntimeException("For Client " + clientId + " don't have any strategy to poll data");
        }
    }
}
