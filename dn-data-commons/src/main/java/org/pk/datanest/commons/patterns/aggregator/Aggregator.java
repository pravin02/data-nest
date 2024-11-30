package org.pk.datanest.commons.patterns.aggregator;

public interface Aggregator<T, S> {

    void aggregate(T content, S specification);

}
