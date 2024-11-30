package org.pk.datanest.aggregator.patterns.notifier;


public interface StatusNotifier<Status, Throwable> extends Notifier {

    void notifyStatus(Status status, Throwable t);
}
