package com.ernoxin.zarinpaljavasdk.callback;

/**
 * Callback status values returned by Zarinpal.
 *
 */
public enum ZarinpalCallbackStatus {
    /** Payment was accepted and is eligible for verification. */
    OK,
    /** Payment was canceled or failed before verification. */
    NOK
}
