package com.ernoxin.zarinpaljavasdk.model;

import java.util.List;

public record VerifyResult(
        int code,
        String message,
        String cardHash,
        String cardPan,
        long refId,
        ZarinpalFeeType feeType,
        long fee,
        List<PaymentWage> wages
) {
}
