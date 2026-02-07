package com.ernoxin.zarinpaljavasdk.model;

/**
 * Inquiry status values returned by Zarinpal.
 *
 */
public enum ZarinpalInquiryStatus {
    /** Transaction is verified successfully. */
    VERIFIED,
    /** Transaction is paid but not fully verified in current flow. */
    PAID,
    /** Transaction is still in bank processing stage. */
    IN_BANK,
    /** Transaction failed. */
    FAILED,
    /** Transaction has been reversed. */
    REVERSED
}
