package org.pk.datanest.commons.patterns.strategy;


public abstract class PullStrategy<T, R> implements Strategy<T, R> {

    public abstract R execute(T t);

}
