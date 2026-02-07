package com.ernoxin.zarinpaljavasdk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.time.Duration;

/**
 * Spring Boot bindable properties for the SDK.
 *
 * <p>Bound from the {@code zarinpal.*} prefix and converted into a validated
 * {@link ZarinpalConfig} via {@link #toConfig()}.
 *
 * <p>Validation is applied during conversion, not during binding.
 *
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "zarinpal")
public class ZarinpalProperties {
    /** Merchant UUID assigned by Zarinpal. */
    private String merchantId;
    /** Default callback URL used by payment requests without request-level callback URL. */
    private URI callbackUrl;
    /** Runtime environment; defaults to production. */
    private ZarinpalEnvironment environment = ZarinpalEnvironment.PRODUCTION;
    /** Base URL overrides. */
    private BaseUrl baseUrl = new BaseUrl();
    /** Operation version segment, default is {@code v4}. */
    private String operationVersion = ZarinpalConfig.DEFAULT_OPERATION_VERSION;
    /** Connect/read timeout values. */
    private Timeout timeout = new Timeout();
    /** Transport retry options. */
    private Retry retry = new Retry();
    /** HTTP options such as {@code User-Agent}. */
    private Http http = new Http();
    /** Maximum amount allowed for toman requests ({@code IRT}). */
    private long maxAmountIrt = ZarinpalConfig.DEFAULT_MAX_AMOUNT_IRT;
    /** Maximum amount allowed for rial requests ({@code IRR}). */
    private long maxAmountIrr = ZarinpalConfig.DEFAULT_MAX_AMOUNT_IRR;
    /** Minimum amount allowed for each wage entry. */
    private long minWageAmount = ZarinpalConfig.DEFAULT_MIN_WAGE_AMOUNT;

    /**
     * Converts bound properties to a validated immutable config.
     *
     * @return validated config
     */
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
                httpValue.getUserAgent(),
                maxAmountIrt,
                maxAmountIrr,
                minWageAmount
        );
    }

    /**
     * Property group for overriding gateway base URLs.
     */
    @Setter
    @Getter
    public static class BaseUrl {
        /** Production base URL override. */
        private URI production = ZarinpalConfig.DEFAULT_BASE_URL_PRODUCTION;
        /** Sandbox base URL override. */
        private URI sandbox = ZarinpalConfig.DEFAULT_BASE_URL_SANDBOX;
    }

    /**
     * Property group for HTTP timeouts.
     */
    @Setter
    @Getter
    public static class Timeout {
        /** Connection establishment timeout. */
        private Duration connect = ZarinpalConfig.DEFAULT_CONNECT_TIMEOUT;
        /** Response read timeout after connection is established. */
        private Duration read = ZarinpalConfig.DEFAULT_READ_TIMEOUT;
    }

    /**
     * Property group for network retry behavior.
     */
    @Setter
    @Getter
    public static class Retry {
        /** Enables retry on transport-level failures. */
        private boolean enabled = ZarinpalConfig.DEFAULT_RETRY_ENABLED;
        /** Total number of attempts when retry is enabled. */
        private int maxAttempts = ZarinpalConfig.DEFAULT_RETRY_MAX_ATTEMPTS;
        /** Fixed backoff between attempts. */
        private Duration backoff = ZarinpalConfig.DEFAULT_RETRY_BACKOFF;
    }

    /**
     * Property group for HTTP metadata.
     */
    @Setter
    @Getter
    public static class Http {
        /** Value used for the outbound {@code User-Agent} header. */
        private String userAgent = ZarinpalConfig.DEFAULT_USER_AGENT;
    }
}
