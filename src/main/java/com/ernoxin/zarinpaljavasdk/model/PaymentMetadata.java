package com.ernoxin.zarinpaljavasdk.model;

public record PaymentMetadata(
        String mobile,
        String email,
        String orderId,
        Boolean autoVerify,
        String cardPan
) {
}
