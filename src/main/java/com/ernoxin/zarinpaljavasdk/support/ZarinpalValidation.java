package com.ernoxin.zarinpaljavasdk.support;

import com.ernoxin.zarinpaljavasdk.exception.ZarinpalValidationException;
import lombok.experimental.UtilityClass;

import java.net.URI;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Shared validation helpers used across config and request processing.
 *
 */
@UtilityClass
public class ZarinpalValidation {
    private static final Pattern AUTHORITY_PATTERN = Pattern.compile("^[AS][A-Za-z0-9]{35}$");
    private static final Pattern IBAN_PATTERN = Pattern.compile("^IR\\d{24}$");
    private static final Pattern CARD_PAN_PATTERN = Pattern.compile("^[0-9Xx]{16}$");

    /**
     * Requires a non-blank string value.
     *
     * @param value value to validate
     * @param field logical field name used in error messages
     * @throws ZarinpalValidationException when value is {@code null} or blank
     */
    public static void requireNonBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new ZarinpalValidationException(field + " is required");
        }
    }

    /**
     * Requires a strictly positive number.
     *
     * @param value value to validate
     * @param field logical field name used in error messages
     * @throws ZarinpalValidationException when value is zero or negative
     */
    public static void requirePositive(long value, String field) {
        if (value <= 0) {
            throw new ZarinpalValidationException(field + " must be positive");
        }
    }

    /**
     * Requires a value greater than or equal to {@code min}.
     *
     * @param value value to validate
     * @param min minimum allowed value
     * @param field logical field name used in error messages
     * @throws ZarinpalValidationException when value is below minimum
     */
    public static void requireMin(long value, long min, String field) {
        if (value < min) {
            throw new ZarinpalValidationException(field + " must be at least " + min);
        }
    }

    /**
     * Requires a nullable value to be non-negative when provided.
     *
     * @param value nullable value
     * @param field logical field name used in error messages
     * @throws ZarinpalValidationException when value is negative
     */
    public static void requireNonNegative(Long value, String field) {
        if (value == null) {
            return;
        }
        if (value < 0) {
            throw new ZarinpalValidationException(field + " must be non-negative");
        }
    }

    /**
     * Requires a string length to be at most {@code max}.
     *
     * @param value value to validate, {@code null} is accepted
     * @param max maximum length
     * @param field logical field name used in error messages
     * @throws ZarinpalValidationException when value length exceeds max
     */
    public static void requireMaxLength(String value, int max, String field) {
        if (value != null && value.length() > max) {
            throw new ZarinpalValidationException(field + " must be at most " + max + " characters");
        }
    }

    /**
     * Requires an absolute HTTP or HTTPS URI.
     *
     * @param uri URI value
     * @param field logical field name used in error messages
     * @throws ZarinpalValidationException when URI is missing or uses unsupported scheme
     */
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

    /**
     * Requires an authority token to follow Zarinpal authority format.
     *
     * @param authority authority token
     * @throws ZarinpalValidationException when authority is blank or malformed
     */
    public static void requireAuthority(String authority) {
        requireNonBlank(authority, "authority");
        String trimmed = authority.trim();
        if (!AUTHORITY_PATTERN.matcher(trimmed).matches()) {
            throw new ZarinpalValidationException("authority must start with A or S and be 36 characters long");
        }
    }

    /**
     * Requires an IBAN in {@code IR} + 24 digits format.
     *
     * @param iban iban value
     * @throws ZarinpalValidationException when iban is blank or malformed
     */
    public static void requireIban(String iban) {
        requireNonBlank(iban, "wages.iban");
        String normalized = iban.trim().toUpperCase(Locale.ROOT);
        if (!IBAN_PATTERN.matcher(normalized).matches()) {
            throw new ZarinpalValidationException("wages.iban must start with IR and be 26 characters");
        }
    }

    /**
     * Requires a card PAN mask/value with 16 characters containing digits or {@code X/x}.
     *
     * @param cardPan card PAN mask/value
     * @throws ZarinpalValidationException when value is blank or malformed
     */
    public static void requireCardPan(String cardPan) {
        requireNonBlank(cardPan, "metadata.cardPan");
        String trimmed = cardPan.trim();
        if (!CARD_PAN_PATTERN.matcher(trimmed).matches()) {
            throw new ZarinpalValidationException("metadata.cardPan must be 16 characters of digits or X");
        }
    }

    /**
     * Returns whether the given value is a valid UUID string.
     *
     * @param value value to parse
     * @return {@code true} when value is a valid UUID
     */
    public static boolean isValidUuid(String value) {
        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Removes trailing slash characters from a base URL.
     *
     * @param uri URI to normalize
     * @return normalized URI without trailing slashes
     */
    public static URI normalizeBaseUrl(URI uri) {
        String value = uri.toString();
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        return URI.create(value);
    }
}
