package org.pk.datanest.puller.patterns.strategy;

import java.util.Map;

public interface Strategy {

    Object execute(Map<String, String> dataMap);

    void executionCompleted(Map<String, String> dataMap);
}
