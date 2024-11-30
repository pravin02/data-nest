package org.pk.datanest.puller.model;

import org.pk.datanest.commons.patterns.strategy.ConnectionMode;

import java.util.Objects;

public class Client {

    private String id;
    private String name;
    private ConnectionMode connectionMode;

    public Client() {
    }

    public Client(String id, String name, ConnectionMode connectionMode) {
        this.id = id;
        this.name = name;
        this.connectionMode = connectionMode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConnectionMode getConnectionMode() {
        return connectionMode;
    }

    public void setConnectionMode(ConnectionMode connectionMode) {
        this.connectionMode = connectionMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return Objects.equals(id, client.id) && Objects.equals(name, client.name) && connectionMode == client.connectionMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, connectionMode);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", connectionMode=" + connectionMode +
                '}';
    }
}
