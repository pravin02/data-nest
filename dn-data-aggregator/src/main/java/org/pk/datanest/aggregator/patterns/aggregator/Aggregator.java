package org.pk.datanest.aggregator.patterns.aggregator;

public interface Aggregator<T, S> {

    void aggregate(T content, S specification);

}
