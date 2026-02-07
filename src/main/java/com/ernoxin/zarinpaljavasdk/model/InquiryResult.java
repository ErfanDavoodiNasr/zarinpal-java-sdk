package com.ernoxin.zarinpaljavasdk.model;

/**
 * Result returned by inquiry operation.
 *
 * @param status inquiry status
 * @param code gateway response code
 * @param message gateway response message
 */
public record InquiryResult(
        ZarinpalInquiryStatus status,
        int code,
        String message
) {
}
