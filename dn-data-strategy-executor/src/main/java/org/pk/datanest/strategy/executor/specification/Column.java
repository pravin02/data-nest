package org.pk.datanest.strategy.executor.specification;


import java.util.List;

public class Column {

    private String column;
    private List<Rule> rules;

    public Column() {
    }

    public Column(String column, List<Rule> rules) {
        this.column = column;
        this.rules = rules;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
