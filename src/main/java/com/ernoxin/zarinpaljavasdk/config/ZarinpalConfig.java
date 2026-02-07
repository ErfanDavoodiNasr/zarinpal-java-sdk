package com.ernoxin.zarinpaljavasdk.config;

import com.ernoxin.zarinpaljavasdk.exception.ZarinpalValidationException;
import com.ernoxin.zarinpaljavasdk.support.ZarinpalValidation;

import java.net.URI;
import java.time.Duration;
import java.util.Locale;

/**
 * Immutable runtime configuration for {@code zarinpal-java-sdk}.
 *
 * <p>This type centralizes merchant identity, gateway URLs, timeout/retry strategy,
 * user-agent, and validation thresholds for amount and wages.
 *
 * <p>Amounts are interpreted according to request currency:
 * {@code IRR} for rial and {@code IRT} for toman.
 *
 * <p>Thread-safety: this record is immutable and thread-safe.
 *
 * @param merchantId merchant UUID assigned by Zarinpal (required)
 * @param environment target environment, defaults to {@link ZarinpalEnvironment#PRODUCTION} when {@code null}
 * @param callbackUrl default callback URL used when request-level callback URL is not provided (required)
 * @param connectTimeout socket/connect timeout for outbound HTTP calls, must be positive
 * @param readTimeout read timeout for outbound HTTP calls, must be positive
 * @param baseUrlProduction base service URL used in production mode
 * @param baseUrlSandbox base service URL used in sandbox mode
 * @param operationVersion API operation version path segment (for example {@code v4})
 * @param retryEnabled whether transport retries are enabled
 * @param retryMaxAttempts total number of attempts when retry is enabled (minimum {@code 1})
 * @param retryBackoff fixed delay between retry attempts, must be non-negative
 * @param userAgent HTTP {@code User-Agent} header value
 * @param maxAmountIrt maximum payment amount for requests in toman ({@code IRT})
 * @param maxAmountIrr maximum payment amount for requests in rial ({@code IRR})
 * @param minWageAmount minimum amount allowed for each wage split entry
 */
public record ZarinpalConfig(
        String merchantId,
        ZarinpalEnvironment environment,
        URI callbackUrl,
        Duration connectTimeout,
        Duration readTimeout,
        URI baseUrlProduction,
        URI baseUrlSandbox,
        String operationVersion,
        boolean retryEnabled,
        int retryMaxAttempts,
        Duration retryBackoff,
        String userAgent,
        long maxAmountIrt,
        long maxAmountIrr,
        long minWageAmount
) {
    /** Default connect timeout ({@code 10s}). */
    public static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofSeconds(10);
    /** Default read timeout ({@code 30s}). */
    public static final Duration DEFAULT_READ_TIMEOUT = Duration.ofSeconds(30);
    /** Default production base URL ({@code https://payment.zarinpal.com}). */
    public static final URI DEFAULT_BASE_URL_PRODUCTION = ZarinpalEnvironment.PRODUCTION.baseUrl();
    /** Default sandbox base URL ({@code https://sandbox.zarinpal.com}). */
    public static final URI DEFAULT_BASE_URL_SANDBOX = ZarinpalEnvironment.SANDBOX.baseUrl();
    /** Default operation version ({@code v4}). */
    public static final String DEFAULT_OPERATION_VERSION = "v4";
    /** Default retry enablement ({@code false}). */
    public static final boolean DEFAULT_RETRY_ENABLED = false;
    /** Default retry attempt count ({@code 1}). */
    public static final int DEFAULT_RETRY_MAX_ATTEMPTS = 1;
    /** Default retry backoff ({@code 0ms}). */
    public static final Duration DEFAULT_RETRY_BACKOFF = Duration.ZERO;
    /** Default HTTP {@code User-Agent} value. */
    public static final String DEFAULT_USER_AGENT = "ZarinpalJavaSdk";
    /** Default maximum amount for toman requests ({@code IRT}). */
    public static final long DEFAULT_MAX_AMOUNT_IRT = 100_000_000L;
    /** Default maximum amount for rial requests ({@code IRR}). */
    public static final long DEFAULT_MAX_AMOUNT_IRR = 1_000_000_000L;
    /** Default minimum amount for each wage split entry. */
    public static final long DEFAULT_MIN_WAGE_AMOUNT = 10_000L;

    /**
     * Creates a validated configuration instance.
     *
     * <p>Null and blank values are normalized to defaults where allowed.
     * Invalid values fail fast with {@link ZarinpalValidationException}.
     *
     * @throws ZarinpalValidationException when one or more fields are invalid
     */
    public ZarinpalConfig {
        if (merchantId == null || merchantId.isBlank()) {
            throw new ZarinpalValidationException("merchantId is required");
        }
        merchantId = merchantId.trim();
        if (!ZarinpalValidation.isValidUuid(merchantId)) {
            throw new ZarinpalValidationException("merchantId must be a valid UUID");
        }
        if (environment == null) {
            environment = ZarinpalEnvironment.PRODUCTION;
        }
        if (callbackUrl == null) {
            throw new ZarinpalValidationException("callbackUrl is required");
        }
        ZarinpalValidation.requireHttpUri(callbackUrl, "callbackUrl");
        if (connectTimeout == null) {
            connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        }
        if (readTimeout == null) {
            readTimeout = DEFAULT_READ_TIMEOUT;
        }
        if (connectTimeout.isZero() || connectTimeout.isNegative()) {
            throw new ZarinpalValidationException("connectTimeout must be positive");
        }
        if (readTimeout.isZero() || readTimeout.isNegative()) {
            throw new ZarinpalValidationException("readTimeout must be positive");
        }
        if (baseUrlProduction == null) {
            baseUrlProduction = DEFAULT_BASE_URL_PRODUCTION;
        }
        if (baseUrlSandbox == null) {
            baseUrlSandbox = DEFAULT_BASE_URL_SANDBOX;
        }
        ZarinpalValidation.requireHttpUri(baseUrlProduction, "baseUrlProduction");
        ZarinpalValidation.requireHttpUri(baseUrlSandbox, "baseUrlSandbox");
        baseUrlProduction = ZarinpalValidation.normalizeBaseUrl(baseUrlProduction);
        baseUrlSandbox = ZarinpalValidation.normalizeBaseUrl(baseUrlSandbox);
        if (operationVersion == null || operationVersion.isBlank()) {
            operationVersion = DEFAULT_OPERATION_VERSION;
        }
        operationVersion = normalizeOperationVersion(operationVersion);
        if (retryBackoff == null) {
            retryBackoff = DEFAULT_RETRY_BACKOFF;
        }
        if (retryMaxAttempts <= 0) {
            throw new ZarinpalValidationException("retryMaxAttempts must be at least 1");
        }
        if (retryBackoff.isNegative()) {
            throw new ZarinpalValidationException("retryBackoff must be non-negative");
        }
        if (userAgent == null || userAgent.isBlank()) {
            userAgent = DEFAULT_USER_AGENT;
        }
        userAgent = userAgent.trim();
        if (maxAmountIrt <= 0) {
            throw new ZarinpalValidationException("maxAmountIrt must be positive");
        }
        if (maxAmountIrr <= 0) {
            throw new ZarinpalValidationException("maxAmountIrr must be positive");
        }
        if (minWageAmount <= 0) {
            throw new ZarinpalValidationException("minWageAmount must be positive");
        }
    }

    private static String normalizeOperationVersion(String operationVersion) {
        String normalized = operationVersion.trim();
        while (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }
        while (normalized.endsWith("/")) {
            normalized = normalized.substring(0, normalized.length() - 1);
        }
        if (normalized.isEmpty()) {
            throw new ZarinpalValidationException("operationVersion is required");
        }
        String lowered = normalized.toLowerCase(Locale.ROOT);
        if (!lowered.startsWith("v")) {
            lowered = "v" + lowered;
        }
        return lowered;
    }

    /**
     * Creates a builder with default values except merchant id.
     *
     * @param merchantId merchant UUID assigned by Zarinpal
     * @return new mutable builder
     */
    public static Builder builder(String merchantId) {
        return new Builder(merchantId);
    }

    /**
     * Returns the normalized base URL for the active environment.
     *
     * @return production or sandbox base URL depending on {@link #environment()}
     */
    public URI baseUrl() {
        URI base = environment == ZarinpalEnvironment.SANDBOX ? baseUrlSandbox : baseUrlProduction;
        return ZarinpalValidation.normalizeBaseUrl(base);
    }

    /**
     * Mutable builder for creating validated {@link ZarinpalConfig} instances.
     *
     * <p>The built config applies the same validation and defaulting rules as the
     * canonical constructor.
     */
    public static final class Builder {
        private final String merchantId;
        private ZarinpalEnvironment environment = ZarinpalEnvironment.PRODUCTION;
        private URI callbackUrl;
        private Duration connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        private Duration readTimeout = DEFAULT_READ_TIMEOUT;
        private URI baseUrlProduction = DEFAULT_BASE_URL_PRODUCTION;
        private URI baseUrlSandbox = DEFAULT_BASE_URL_SANDBOX;
        private String operationVersion = DEFAULT_OPERATION_VERSION;
        private boolean retryEnabled = DEFAULT_RETRY_ENABLED;
        private int retryMaxAttempts = DEFAULT_RETRY_MAX_ATTEMPTS;
        private Duration retryBackoff = DEFAULT_RETRY_BACKOFF;
        private String userAgent = DEFAULT_USER_AGENT;
        private long maxAmountIrt = DEFAULT_MAX_AMOUNT_IRT;
        private long maxAmountIrr = DEFAULT_MAX_AMOUNT_IRR;
        private long minWageAmount = DEFAULT_MIN_WAGE_AMOUNT;

        /**
         * Creates a builder for the given merchant id.
         *
         * @param merchantId merchant UUID assigned by Zarinpal
         */
        public Builder(String merchantId) {
            this.merchantId = merchantId;
        }

        /**
         * Sets the target environment.
         *
         * @param environment environment to use for requests
         * @return this builder
         */
        public Builder environment(ZarinpalEnvironment environment) {
            this.environment = environment;
            return this;
        }

        /**
         * Sets the default callback URL.
         *
         * @param callbackUrl callback URL used when a request-level callback is absent
         * @return this builder
         */
        public Builder callbackUrl(URI callbackUrl) {
            this.callbackUrl = callbackUrl;
            return this;
        }

        /**
         * Sets the connect timeout.
         *
         * @param connectTimeout connect timeout duration
         * @return this builder
         */
        public Builder connectTimeout(Duration connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * Sets the read timeout.
         *
         * @param readTimeout read timeout duration
         * @return this builder
         */
        public Builder readTimeout(Duration readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * Sets the production base URL.
         *
         * @param baseUrlProduction production base URL
         * @return this builder
         */
        public Builder baseUrlProduction(URI baseUrlProduction) {
            this.baseUrlProduction = baseUrlProduction;
            return this;
        }

        /**
         * Sets the sandbox base URL.
         *
         * @param baseUrlSandbox sandbox base URL
         * @return this builder
         */
        public Builder baseUrlSandbox(URI baseUrlSandbox) {
            this.baseUrlSandbox = baseUrlSandbox;
            return this;
        }

        /**
         * Sets the operation version path segment.
         *
         * @param operationVersion version like {@code v4}; normalized during build
         * @return this builder
         */
        public Builder operationVersion(String operationVersion) {
            this.operationVersion = operationVersion;
            return this;
        }

        /**
         * Enables or disables transport retries.
         *
         * @param retryEnabled {@code true} to enable retries
         * @return this builder
         */
        public Builder retryEnabled(boolean retryEnabled) {
            this.retryEnabled = retryEnabled;
            return this;
        }

        /**
         * Sets the maximum number of attempts for retry-enabled calls.
         *
         * @param retryMaxAttempts total attempt count (including first attempt)
         * @return this builder
         */
        public Builder retryMaxAttempts(int retryMaxAttempts) {
            this.retryMaxAttempts = retryMaxAttempts;
            return this;
        }

        /**
         * Sets the fixed backoff between retries.
         *
         * @param retryBackoff delay between attempts
         * @return this builder
         */
        public Builder retryBackoff(Duration retryBackoff) {
            this.retryBackoff = retryBackoff;
            return this;
        }

        /**
         * Sets the HTTP {@code User-Agent} header value.
         *
         * @param userAgent user-agent value
         * @return this builder
         */
        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        /**
         * Sets the maximum amount for requests in toman ({@code IRT}).
         *
         * @param maxAmountIrt max amount for {@code IRT}
         * @return this builder
         */
        public Builder maxAmountIrt(long maxAmountIrt) {
            this.maxAmountIrt = maxAmountIrt;
            return this;
        }

        /**
         * Sets the maximum amount for requests in rial ({@code IRR}).
         *
         * @param maxAmountIrr max amount for {@code IRR}
         * @return this builder
         */
        public Builder maxAmountIrr(long maxAmountIrr) {
            this.maxAmountIrr = maxAmountIrr;
            return this;
        }

        /**
         * Sets the minimum amount per wage split.
         *
         * @param minWageAmount minimum amount for each wage entry
         * @return this builder
         */
        public Builder minWageAmount(long minWageAmount) {
            this.minWageAmount = minWageAmount;
            return this;
        }

        /**
         * Builds a validated immutable config.
         *
         * @return validated config instance
         * @throws ZarinpalValidationException when any configured value is invalid
         */
        public ZarinpalConfig build() {
            return new ZarinpalConfig(
                    merchantId,
                    environment,
                    callbackUrl,
                    connectTimeout,
                    readTimeout,
                    baseUrlProduction,
                    baseUrlSandbox,
                    operationVersion,
                    retryEnabled,
                    retryMaxAttempts,
                    retryBackoff,
                    userAgent,
                    maxAmountIrt,
                    maxAmountIrr,
                    minWageAmount
            );
        }
    }
}
