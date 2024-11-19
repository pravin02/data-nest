package org.pk.datanest.puller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Value("${dn.data.dataset.host}")
    String datasetHost;

    @Value("${dn.data.aggregator.host}")
    String aggregatorHost;

    @Bean("datasetHost")
    public RestClient getRestClient() {
        return RestClient.create(datasetHost);
    }

    @Bean("aggregatorHost")
    public RestClient getRestClientForAggregator() {
        return RestClient.create(aggregatorHost);
    }

}
