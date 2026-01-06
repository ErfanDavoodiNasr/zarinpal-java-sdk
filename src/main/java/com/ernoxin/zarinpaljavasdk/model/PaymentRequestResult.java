package com.ernoxin.zarinpaljavasdk.model;

public record PaymentRequestResult(
        int code,
        String message,
        String authority,
        ZarinpalFeeType feeType,
        long fee
) {
}
