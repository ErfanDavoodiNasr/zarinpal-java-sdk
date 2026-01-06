package com.ernoxin.zarinpaljavasdk.support;

import com.ernoxin.zarinpaljavasdk.exception.ZarinpalValidationException;
import lombok.experimental.UtilityClass;

import java.net.URI;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

@UtilityClass
public class ZarinpalValidation {
    private static final Pattern AUTHORITY_PATTERN = Pattern.compile("^[AS][A-Za-z0-9]+$");
    private static final Pattern IBAN_PATTERN = Pattern.compile("^IR\\d{24}$");
    private static final Pattern CARD_PAN_PATTERN = Pattern.compile("^[0-9Xx]{16}$");

    public static void requireNonBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new ZarinpalValidationException(field + " is required");
        }
    }

    public static void requirePositive(long value, String field) {
        if (value <= 0) {
            throw new ZarinpalValidationException(field + " must be positive");
        }
    }

    public static void requireMin(long value, long min, String field) {
        if (value < min) {
            throw new ZarinpalValidationException(field + " must be at least " + min);
        }
    }

    public static void requireNonNegative(Long value, String field) {
        if (value == null) {
            return;
        }
        if (value < 0) {
            throw new ZarinpalValidationException(field + " must be non-negative");
        }
    }

    public static void requireMaxLength(String value, int max, String field) {
        if (value != null && value.length() > max) {
            throw new ZarinpalValidationException(field + " must be at most " + max + " characters");
        }
    }

    public static void requireHttpUri(URI uri, String field) {
        if (uri == null) {
            throw new ZarinpalValidationException(field + " is required");
        }
        if (!uri.isAbsolute() || uri.getScheme() == null) {
            throw new ZarinpalValidationException(field + " must be an absolute URL");
        }
        String scheme = uri.getScheme().toLowerCase(Locale.ROOT);
        if (!scheme.equals("http") && !scheme.equals("https")) {
            throw new ZarinpalValidationException(field + " must use http or https");
        }
    }

    public static void requireAuthority(String authority) {
        requireNonBlank(authority, "authority");
        String trimmed = authority.trim();
        if (!AUTHORITY_PATTERN.matcher(trimmed).matches()) {
            throw new ZarinpalValidationException("authority must start with A or S and contain only letters and digits");
        }
    }

    public static void requireIban(String iban) {
        requireNonBlank(iban, "wages.iban");
        String normalized = iban.trim().toUpperCase(Locale.ROOT);
        if (!IBAN_PATTERN.matcher(normalized).matches()) {
            throw new ZarinpalValidationException("wages.iban must start with IR and be 26 characters");
        }
    }

    public static void requireCardPan(String cardPan) {
        requireNonBlank(cardPan, "metadata.cardPan");
        String trimmed = cardPan.trim();
        if (!CARD_PAN_PATTERN.matcher(trimmed).matches()) {
            throw new ZarinpalValidationException("metadata.cardPan must be 16 characters of digits or X");
        }
    }

    public static boolean isValidUuid(String value) {
        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public static URI normalizeBaseUrl(URI uri) {
        String value = uri.toString();
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        return URI.create(value);
    }
}
