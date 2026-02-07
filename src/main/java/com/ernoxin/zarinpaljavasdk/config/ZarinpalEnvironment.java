package com.ernoxin.zarinpaljavasdk.config;

import java.net.URI;

/**
 * Supported runtime environments for Zarinpal gateway calls.
 *
 */
public enum ZarinpalEnvironment {
    /** Production gateway endpoints. */
    PRODUCTION("https://payment.zarinpal.com"),
    /** Sandbox gateway endpoints for testing. */
    SANDBOX("https://sandbox.zarinpal.com");

    private final URI baseUrl;

    ZarinpalEnvironment(String baseUrl) {
        this.baseUrl = URI.create(baseUrl);
    }

    /**
     * Returns the default base URL for this environment.
     *
     * @return environment base URL
     */
    public URI baseUrl() {
        return baseUrl;
    }
}
