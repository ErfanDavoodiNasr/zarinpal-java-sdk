package com.ernoxin.zarinpaljavasdk.config;

import java.net.URI;

public enum ZarinpalEnvironment {
    PRODUCTION("https://payment.zarinpal.com"),
    SANDBOX("https://sandbox.zarinpal.com");

    private final URI baseUrl;

    ZarinpalEnvironment(String baseUrl) {
        this.baseUrl = URI.create(baseUrl);
    }

    public URI baseUrl() {
        return baseUrl;
    }
}
