package com.ernoxin.zarinpaljavasdk.model;

import java.util.List;

public record UnverifiedResult(
        int code,
        String message,
        List<UnverifiedAuthority> authorities
) {
}
