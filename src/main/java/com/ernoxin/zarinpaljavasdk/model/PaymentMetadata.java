package com.ernoxin.zarinpaljavasdk.model;

/**
 * Optional metadata sent with a payment request.
 *
 * @param mobile payer mobile number (optional, non-blank when provided)
 * @param email payer email (optional, non-blank when provided)
 * @param orderId merchant order identifier (optional, non-blank when provided)
 * @param autoVerify whether gateway should automatically verify payment when supported
 * @param cardPan optional PAN mask/value to restrict payment card; must be 16 chars of digits or {@code X/x}
 */
public record PaymentMetadata(
        String mobile,
        String email,
        String orderId,
        Boolean autoVerify,
        String cardPan
) {
}
