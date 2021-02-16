package com.xyz.rate.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateConfiguration {

    @Value("${rates.url}")
    private String rateUrl;
    @Value("${rates.timeout.connection}")
    private String rateConnectionTimeout;
    @Value("${rates.timeout.read}")
    private String rateReadTimeout;

    public String getRateUrl() {
        return rateUrl;
    }

    public String getRateConnectionTimeout() {
        return rateConnectionTimeout;
    }

    public String getRateReadTimeout() {
        return rateReadTimeout;
    }
}
