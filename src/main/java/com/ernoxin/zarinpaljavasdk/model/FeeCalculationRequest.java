package com.ernoxin.zarinpaljavasdk.model;

/**
 * Fee calculation request payload.
 *
 * @param amount amount to calculate fee for; SDK enforces minimum 1000
 * @param currency currency unit ({@code IRR} or {@code IRT})
 */
public record FeeCalculationRequest(
        long amount,
        ZarinpalCurrency currency
) {
}
