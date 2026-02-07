package com.ernoxin.zarinpaljavasdk.support;

import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * Catalog of known Zarinpal gateway codes to human-readable messages.
 *
 * <p>This catalog is best-effort and used as a fallback when response messages
 * are missing or empty.
 *
 */
@UtilityClass
public class ZarinpalErrorCatalog {
    private static final Map<Integer, String> MESSAGES = Map.ofEntries(
            Map.entry(-9, "Validation error"),
            Map.entry(-2, "Callback URL is required"),
            Map.entry(-3, "Description is required or too long"),
            Map.entry(-4, "Amount is invalid"),
            Map.entry(-5, "Referrer ID is invalid"),
            Map.entry(-10, "Terminal is not valid, please check merchant_id or ip address."),
            Map.entry(-11, "Terminal is not active, please contact our support team."),
            Map.entry(-12, "To many attempts, please try again later."),
            Map.entry(-13, "Terminal limit reached."),
            Map.entry(-14, "The callback URL domain does not match the registered terminal domain."),
            Map.entry(-15, "Terminal user is suspend : (please contact our support team)."),
            Map.entry(-16, "Terminal user level is not valid : ( please contact our support team)."),
            Map.entry(-17, "Terminal user level is not valid : ( please contact our support team)."),
            Map.entry(-18, "The referrer address does not match the registered domain."),
            Map.entry(-19, "Terminal user transactions are banned."),
            Map.entry(100, "Success"),
            Map.entry(-30, "Terminal do not allow to accept floating wages."),
            Map.entry(-31, "Terminal do not allow to accept wages, please add default bank account in panel."),
            Map.entry(-32, "Wages is not valid, Total wages(floating) has been overload max amount."),
            Map.entry(-33, "Wages floating is not valid."),
            Map.entry(-34, "Wages is not valid, Total wages(fixed) has been overload max amount."),
            Map.entry(-35, "Wages is not valid, Total wages(floating) has been reached the limit in max parts."),
            Map.entry(-36, "The minimum amount for wages(floating) should be 10,000 Rials"),
            Map.entry(-37, "One or more iban entered for wages(floating) from the bank side are inactive."),
            Map.entry(-38, "Wages need to set Iban in shaparak."),
            Map.entry(-39, "Wages have a error!"),
            Map.entry(-40, "Invalid extra params, expire_in is not valid."),
            Map.entry(-41, "Maximum amount is 100,000,000 tomans."),
            Map.entry(-50, "Session is not valid, amounts values is not the same."),
            Map.entry(-51, "Session is not valid, session is not active paid try."),
            Map.entry(-52, "Oops!!, please contact our support team"),
            Map.entry(-53, "Session is not this merchant_id session"),
            Map.entry(-54, "Invalid authority."),
            Map.entry(-55, "Manual payment request not found."),
            Map.entry(-60, "Session can not be reversed with bank."),
            Map.entry(-61, "Session is not in success status."),
            Map.entry(-62, "Terminal ip limit most be active."),
            Map.entry(-63, "Maximum time for reverse this session is expired."),
            Map.entry(101, "Verified"),
            Map.entry(429, "Referrer code format is invalid.")
    );

    /**
     * Returns catalog message for a gateway code.
     *
     * @param code gateway code
     * @return catalog message or {@code null} when code is unknown
     */
    public static String messageFor(Integer code) {
        if (code == null) {
            return null;
        }
        return MESSAGES.get(code);
    }
}
