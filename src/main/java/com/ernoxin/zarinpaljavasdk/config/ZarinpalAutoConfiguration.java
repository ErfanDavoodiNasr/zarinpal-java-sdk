package com.ernoxin.zarinpaljavasdk.config;

import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
import com.ernoxin.zarinpaljavasdk.http.ZarinpalHttpClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot auto-configuration for SDK beans.
 *
 * <p>Creates {@link ZarinpalConfig}, {@link ZarinpalHttpClient}, and
 * {@link ZarinpalClient} when no user-defined beans of the same type exist.
 *
 */
@AutoConfiguration
@EnableConfigurationProperties(ZarinpalProperties.class)
public class ZarinpalAutoConfiguration {

    /**
     * Creates a validated config object from bound properties.
     *
     * @param properties bound {@code zarinpal.*} properties
     * @return immutable SDK config
     */
    @Bean
    @ConditionalOnMissingBean
    public ZarinpalConfig zarinpalConfig(ZarinpalProperties properties) {
        return properties.toConfig();
    }

    /**
     * Creates the default HTTP client.
     *
     * @param config validated SDK config
     * @return HTTP client used by {@link ZarinpalClient}
     */
    @Bean
    @ConditionalOnMissingBean
    public ZarinpalHttpClient zarinpalHttpClient(ZarinpalConfig config) {
        return ZarinpalHttpClient.create(config);
    }

    /**
     * Creates the high-level SDK client.
     *
     * @param config validated SDK config
     * @param httpClient HTTP transport implementation
     * @return reusable SDK client bean
     */
    @Bean
    @ConditionalOnMissingBean
    public ZarinpalClient zarinpalClient(ZarinpalConfig config, ZarinpalHttpClient httpClient) {
        return new ZarinpalClient(config, httpClient);
    }
}
