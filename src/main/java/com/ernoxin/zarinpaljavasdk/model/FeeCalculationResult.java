package com.ernoxin.zarinpaljavasdk.model;

public record FeeCalculationResult(
        long amount,
        long fee,
        ZarinpalFeeType feeType,
        String suggestedAmount,
        int code,
        String message
) {
}
