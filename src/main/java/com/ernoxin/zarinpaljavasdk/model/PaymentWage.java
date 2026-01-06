package com.ernoxin.zarinpaljavasdk.model;

public record PaymentWage(
        String iban,
        long amount,
        String description
) {
}
