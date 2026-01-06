package com.ernoxin.zarinpaljavasdk.config;

import com.ernoxin.zarinpaljavasdk.exception.ZarinpalValidationException;
import com.ernoxin.zarinpaljavasdk.support.ZarinpalValidation;

import java.net.URI;
import java.time.Duration;
import java.util.Locale;

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
        String userAgent
) {
    public static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofSeconds(10);
    public static final Duration DEFAULT_READ_TIMEOUT = Duration.ofSeconds(30);
    public static final URI DEFAULT_BASE_URL_PRODUCTION = ZarinpalEnvironment.PRODUCTION.baseUrl();
    public static final URI DEFAULT_BASE_URL_SANDBOX = ZarinpalEnvironment.SANDBOX.baseUrl();
    public static final String DEFAULT_OPERATION_VERSION = "v4";
    public static final boolean DEFAULT_RETRY_ENABLED = false;
    public static final int DEFAULT_RETRY_MAX_ATTEMPTS = 1;
    public static final Duration DEFAULT_RETRY_BACKOFF = Duration.ZERO;
    public static final String DEFAULT_USER_AGENT = "ZarinpalJavaSdk";

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

    public static Builder builder(String merchantId) {
        return new Builder(merchantId);
    }

    public URI baseUrl() {
        URI base = environment == ZarinpalEnvironment.SANDBOX ? baseUrlSandbox : baseUrlProduction;
        return ZarinpalValidation.normalizeBaseUrl(base);
    }

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

        public Builder(String merchantId) {
            this.merchantId = merchantId;
        }

        public Builder environment(ZarinpalEnvironment environment) {
            this.environment = environment;
            return this;
        }

        public Builder callbackUrl(URI callbackUrl) {
            this.callbackUrl = callbackUrl;
            return this;
        }

        public Builder connectTimeout(Duration connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder readTimeout(Duration readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder baseUrlProduction(URI baseUrlProduction) {
            this.baseUrlProduction = baseUrlProduction;
            return this;
        }

        public Builder baseUrlSandbox(URI baseUrlSandbox) {
            this.baseUrlSandbox = baseUrlSandbox;
            return this;
        }

        public Builder operationVersion(String operationVersion) {
            this.operationVersion = operationVersion;
            return this;
        }

        public Builder retryEnabled(boolean retryEnabled) {
            this.retryEnabled = retryEnabled;
            return this;
        }

        public Builder retryMaxAttempts(int retryMaxAttempts) {
            this.retryMaxAttempts = retryMaxAttempts;
            return this;
        }

        public Builder retryBackoff(Duration retryBackoff) {
            this.retryBackoff = retryBackoff;
            return this;
        }

        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

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
                    userAgent
            );
        }
    }
}
