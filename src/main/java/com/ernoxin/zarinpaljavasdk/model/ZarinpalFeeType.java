package com.ernoxin.zarinpaljavasdk.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum ZarinpalFeeType {
    MERCHANT("Merchant"),
    PAYER("Payer");

    private final String value;

    ZarinpalFeeType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ZarinpalFeeType fromValue(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim().toLowerCase(Locale.ROOT);
        if (normalized.equals("merchant")) {
            return MERCHANT;
        }
        if (normalized.equals("payer")) {
            return PAYER;
        }
        throw new IllegalArgumentException("Unknown fee type: " + value);
    }

    @JsonValue
    public String value() {
        return value;
    }
}
