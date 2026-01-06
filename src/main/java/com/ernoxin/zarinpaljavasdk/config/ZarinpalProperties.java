package com.ernoxin.zarinpaljavasdk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.time.Duration;

@Setter
@Getter
@ConfigurationProperties(prefix = "zarinpal")
public class ZarinpalProperties {
    private String merchantId;
    private URI callbackUrl;
    private ZarinpalEnvironment environment = ZarinpalEnvironment.PRODUCTION;
    private BaseUrl baseUrl = new BaseUrl();
    private String operationVersion = ZarinpalConfig.DEFAULT_OPERATION_VERSION;
    private Timeout timeout = new Timeout();
    private Retry retry = new Retry();
    private Http http = new Http();

    public ZarinpalConfig toConfig() {
        BaseUrl baseUrlValue = baseUrl != null ? baseUrl : new BaseUrl();
        Timeout timeoutValue = timeout != null ? timeout : new Timeout();
        Retry retryValue = retry != null ? retry : new Retry();
        Http httpValue = http != null ? http : new Http();
        return new ZarinpalConfig(
                merchantId,
                environment,
                callbackUrl,
                timeoutValue.getConnect(),
                timeoutValue.getRead(),
                baseUrlValue.getProduction(),
                baseUrlValue.getSandbox(),
                operationVersion,
                retryValue.isEnabled(),
                retryValue.getMaxAttempts(),
                retryValue.getBackoff(),
                httpValue.getUserAgent()
        );
    }

    @Setter
    @Getter
    public static class BaseUrl {
        private URI production = ZarinpalConfig.DEFAULT_BASE_URL_PRODUCTION;
        private URI sandbox = ZarinpalConfig.DEFAULT_BASE_URL_SANDBOX;
    }

    @Setter
    @Getter
    public static class Timeout {
        private Duration connect = ZarinpalConfig.DEFAULT_CONNECT_TIMEOUT;
        private Duration read = ZarinpalConfig.DEFAULT_READ_TIMEOUT;
    }

    @Setter
    @Getter
    public static class Retry {
        private boolean enabled = ZarinpalConfig.DEFAULT_RETRY_ENABLED;
        private int maxAttempts = ZarinpalConfig.DEFAULT_RETRY_MAX_ATTEMPTS;
        private Duration backoff = ZarinpalConfig.DEFAULT_RETRY_BACKOFF;
    }

    @Setter
    @Getter
    public static class Http {
        private String userAgent = ZarinpalConfig.DEFAULT_USER_AGENT;
    }
}
