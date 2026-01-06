package com.ernoxin.zarinpaljavasdk.model;

public record InquiryResult(
        ZarinpalInquiryStatus status,
        int code,
        String message
) {
}
