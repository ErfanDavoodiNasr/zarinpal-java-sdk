package com.ernoxin.zarinpaljavasdk.model;

public record UnverifiedAuthority(
        String authority,
        long amount,
        String callbackUrl,
        String referer,
        String date
) {
}
