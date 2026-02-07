package com.ernoxin.zarinpaljavasdk.model;

/**
 * Wage/split payment entry.
 *
 * @param iban target IBAN in {@code IR} + 24 digits format
 * @param amount split amount (must be positive and at least configured minimum)
 * @param description split description (required)
 */
public record PaymentWage(
        String iban,
        long amount,
        String description
) {
}
