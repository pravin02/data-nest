package org.pk.datanest.puller.patterns.notifier;

public interface Notifier<T> {

    void notify(T t);
}
