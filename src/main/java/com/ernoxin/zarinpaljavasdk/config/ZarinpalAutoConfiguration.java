package com.ernoxin.zarinpaljavasdk.config;

import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
import com.ernoxin.zarinpaljavasdk.http.ZarinpalHttpClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(ZarinpalProperties.class)
public class ZarinpalAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ZarinpalConfig zarinpalConfig(ZarinpalProperties properties) {
        return properties.toConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    public ZarinpalHttpClient zarinpalHttpClient(ZarinpalConfig config) {
        return ZarinpalHttpClient.create(config);
    }

    @Bean
    @ConditionalOnMissingBean
    public ZarinpalClient zarinpalClient(ZarinpalConfig config, ZarinpalHttpClient httpClient) {
        return new ZarinpalClient(config, httpClient);
    }
}
