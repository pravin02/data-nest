package org.pk.datanest.puller.patterns.strategy.impl;

import org.pk.datanest.commons.patterns.strategy.Strategy;


public abstract class PollStrategy<T, R> implements Strategy<T, R> {

    public abstract R execute(T t);

}
