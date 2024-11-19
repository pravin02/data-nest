package org.pk.datanest.aggregator.specification;

import java.util.List;
import java.util.Set;

public class JsonSpecification {

    private List<Column> columns;

    public JsonSpecification() {
    }

    public JsonSpecification(Set<Column> columns) {

    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
