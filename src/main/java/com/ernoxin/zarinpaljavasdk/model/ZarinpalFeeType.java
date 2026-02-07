package com.ernoxin.zarinpaljavasdk.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

/**
 * Indicates who pays gateway fee in a transaction.
 *
 */
public enum ZarinpalFeeType {
    /** Merchant pays fee. */
    MERCHANT("Merchant"),
    /** Payer/customer pays fee. */
    PAYER("Payer");

    private final String value;

    ZarinpalFeeType(String value) {
        this.value = value;
    }

    /**
     * Parses gateway fee type from text.
     *
     * @param value raw fee type value
     * @return parsed enum value, or {@code null} when input is {@code null}
     * @throws IllegalArgumentException when value is unknown
     */
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

    /**
     * Returns JSON value expected by gateway.
     *
     * @return serialized fee type value
     */
    @JsonValue
    public String value() {
        return value;
    }
}
