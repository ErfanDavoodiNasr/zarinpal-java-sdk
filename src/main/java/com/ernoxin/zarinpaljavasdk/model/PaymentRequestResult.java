package com.ernoxin.zarinpaljavasdk.model;

/**
 * Result returned by payment request operation.
 *
 * @param code gateway response code
 * @param message gateway response message
 * @param authority authority token used for redirect and verification
 * @param feeType indicates who pays gateway fee
 * @param fee fee amount reported by gateway
 */
public record PaymentRequestResult(
        int code,
        String message,
        String authority,
        ZarinpalFeeType feeType,
        long fee
) {
}
