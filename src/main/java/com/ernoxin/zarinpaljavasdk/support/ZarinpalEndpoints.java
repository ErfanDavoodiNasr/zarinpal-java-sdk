package com.ernoxin.zarinpaljavasdk.support;

import lombok.experimental.UtilityClass;

/**
 * Endpoint path factory for Zarinpal API operations.
 *
 */
@UtilityClass
public class ZarinpalEndpoints {

    /**
     * Builds payment request endpoint path.
     *
     * @param operationVersion normalized operation version (for example {@code v4})
     * @return request endpoint path
     */
    public static String request(String operationVersion) {
        return paymentPath(operationVersion, "request");
    }

    /**
     * Builds payment verification endpoint path.
     *
     * @param operationVersion normalized operation version (for example {@code v4})
     * @return verify endpoint path
     */
    public static String verify(String operationVersion) {
        return paymentPath(operationVersion, "verify");
    }

    /**
     * Builds payment reverse endpoint path.
     *
     * @param operationVersion normalized operation version (for example {@code v4})
     * @return reverse endpoint path
     */
    public static String reverse(String operationVersion) {
        return paymentPath(operationVersion, "reverse");
    }

    /**
     * Builds unverified payments endpoint path.
     *
     * @param operationVersion normalized operation version (for example {@code v4})
     * @return unverified endpoint path
     */
    public static String unverified(String operationVersion) {
        return paymentPath(operationVersion, "unVerified");
    }

    /**
     * Builds payment inquiry endpoint path.
     *
     * @param operationVersion normalized operation version (for example {@code v4})
     * @return inquiry endpoint path
     */
    public static String inquiry(String operationVersion) {
        return paymentPath(operationVersion, "inquiry");
    }

    /**
     * Builds fee-calculation endpoint path.
     *
     * @param operationVersion normalized operation version (for example {@code v4})
     * @return fee-calculation endpoint path
     */
    public static String feeCalculation(String operationVersion) {
        return paymentPath(operationVersion, "feeCalculation");
    }

    /**
     * Returns start-pay redirect path.
     *
     * @return start-pay path prefix ending with slash
     */
    public static String startPay() {
        return "/pg/StartPay/";
    }

    private static String paymentPath(String operationVersion, String action) {
        return "/pg/" + operationVersion + "/payment/" + action + ".json";
    }
}
