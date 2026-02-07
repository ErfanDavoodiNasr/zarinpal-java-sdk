package com.ernoxin.zarinpaljavasdk.model;

/**
 * Unverified authority item returned by unverified operation.
 *
 * @param authority authority token
 * @param amount transaction amount
 * @param callbackUrl callback URL stored for transaction
 * @param referer referrer/referer field returned by gateway
 * @param date gateway date-time representation
 */
public record UnverifiedAuthority(
        String authority,
        long amount,
        String callbackUrl,
        String referer,
        String date
) {
}
