package com.ernoxin.zarinpaljavasdk.model;

/**
 * Verification request payload.
 *
 * @param amount original transaction amount
 * @param authority payment authority returned by request/callback
 */
public record VerifyRequest(
        long amount,
        String authority
) {
}
