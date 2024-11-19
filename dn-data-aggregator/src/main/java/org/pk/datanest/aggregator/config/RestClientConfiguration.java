package org.pk.datanest.aggregator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Value("${dn.data.strategyExecutor.host}")
    String downloadLocation;

    @Bean
    public RestClient getRestClient() {
        return RestClient.create(downloadLocation);
    }
}
