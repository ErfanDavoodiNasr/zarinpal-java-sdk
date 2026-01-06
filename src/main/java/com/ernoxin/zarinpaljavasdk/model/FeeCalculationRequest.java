package com.ernoxin.zarinpaljavasdk.model;

public record FeeCalculationRequest(
        long amount,
        ZarinpalCurrency currency
) {
}
