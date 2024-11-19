package org.pk.datanest.strategy.executor.patterns.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StrategyExecutorFacade {

    @Autowired
    @Qualifier("csvContentStrategy")
    Strategy strategy;

    public void execute(int clientId) throws IOException {
        switch (clientId) {
            case 1 -> {
                CsvStrategyContext context = new CsvStrategyContext();
                context.setCsvFileName("1_aggregated.csv");
                context.setJsonSpecificationFileName("1.json");
                context.setClientId(String.valueOf(clientId));
                strategy.execute(context);
            }
        }
    }
}
