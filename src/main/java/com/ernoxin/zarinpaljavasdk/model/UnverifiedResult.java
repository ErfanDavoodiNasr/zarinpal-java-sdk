package com.ernoxin.zarinpaljavasdk.model;

import java.util.List;

/**
 * Result returned by unverified payments operation.
 *
 * @param code gateway response code
 * @param message gateway response message
 * @param authorities unverified authority entries
 */
public record UnverifiedResult(
        int code,
        String message,
        List<UnverifiedAuthority> authorities
) {
}
