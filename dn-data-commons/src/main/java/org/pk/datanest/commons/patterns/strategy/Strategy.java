package org.pk.datanest.commons.patterns.strategy;

public interface Strategy<T, R> {

    R execute(T context);

}
