package com.ernoxin.zarinpaljavasdk.exception;

/**
 * Thrown when callback query parameters are missing or invalid.
 *
 */
public class ZarinpalCallbackException extends ZarinpalException {

    /**
     * Creates a callback parsing exception.
     *
     * @param message callback validation detail
     */
    public ZarinpalCallbackException(String message) {
        super(message);
    }
}
