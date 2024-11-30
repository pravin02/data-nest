package org.pk.datanest.commons.patterns.notifier;

import org.pk.datanest.commons.patterns.notifier.exception.NotificationFailedException;

public interface ResourceNotifier<T> extends Notifier {

    void notify(T t) throws NotificationFailedException;
}
