package org.pk.datanest.strategy.executor.patterns.strategy;

public class CsvStrategyContext implements StrategyContext {

    private String csvFileName;

    private String jsonSpecificationFileName;

    private String clientId;

    @Override
    public CsvStrategyContext getContext() {
        return null;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCsvFileName() {
        return csvFileName;
    }

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public String getJsonSpecificationFileName() {
        return jsonSpecificationFileName;
    }

    public void setJsonSpecificationFileName(String jsonSpecificationFileName) {
        this.jsonSpecificationFileName = jsonSpecificationFileName;
    }
}
