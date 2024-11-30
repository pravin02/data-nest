package org.pk.datanest.aggregator.patterns.aggregator;

import java.io.IOException;

public interface Aggregator {

    default void started() {

    }

    void aggregate(String content, String specification) throws IOException;

    default void completed() {

    }
}
