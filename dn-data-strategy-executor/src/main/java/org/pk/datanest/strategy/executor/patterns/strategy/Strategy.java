package org.pk.datanest.strategy.executor.patterns.strategy;

import java.io.IOException;

public interface Strategy {

    public void execute(StrategyContext strategyContext) throws IOException;
}
