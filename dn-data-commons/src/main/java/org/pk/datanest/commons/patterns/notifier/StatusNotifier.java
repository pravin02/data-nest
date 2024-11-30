package org.pk.datanest.commons.patterns.notifier;


import org.pk.datanest.commons.patterns.notifier.model.Status;

public interface StatusNotifier extends Notifier {

    void notifyStatus(Status status, Throwable t);
}
