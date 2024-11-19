package org.pk.datanest.aggregator.patterns.aggregator;

import java.io.IOException;

public interface Aggregator {

    public void aggregate(String content, String specification) throws IOException;
}
