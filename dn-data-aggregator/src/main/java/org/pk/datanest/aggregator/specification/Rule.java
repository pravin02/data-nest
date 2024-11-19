package org.pk.datanest.aggregator.specification;

public class Rule {

    private String name;
    private String ruleDef;

    public Rule() {
    }


    public Rule(String name, String ruleDef) {
        this.name = name;
        this.ruleDef = ruleDef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuleDef() {
        return ruleDef;
    }

    public void setRuleDef(String ruleDef) {
        this.ruleDef = ruleDef;
    }
}
