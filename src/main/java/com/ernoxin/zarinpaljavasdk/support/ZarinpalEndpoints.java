package com.ernoxin.zarinpaljavasdk.support;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ZarinpalEndpoints {
    public static String request(String operationVersion) {
        return paymentPath(operationVersion, "request");
    }

    public static String verify(String operationVersion) {
        return paymentPath(operationVersion, "verify");
    }

    public static String reverse(String operationVersion) {
        return paymentPath(operationVersion, "reverse");
    }

    public static String unverified(String operationVersion) {
        return paymentPath(operationVersion, "unVerified");
    }

    public static String inquiry(String operationVersion) {
        return paymentPath(operationVersion, "inquiry");
    }

    public static String feeCalculation(String operationVersion) {
        return paymentPath(operationVersion, "feeCalculation");
    }

    public static String startPay() {
        return "/pg/StartPay/";
    }

    private static String paymentPath(String operationVersion, String action) {
        return "/pg/" + operationVersion + "/payment/" + action + ".json";
    }
}
