package org.pk.datanest.aggregator.patterns.notifier;

import org.pk.datanest.aggregator.patterns.notifier.exception.NotificationFailedException;

public interface ResourceNotifier<T> extends Notifier {

    void notify(T t) throws NotificationFailedException;
}
