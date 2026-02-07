package com.ernoxin.zarinpaljavasdk.exception;

/**
 * Thrown when local input or configuration validation fails before request dispatch.
 *
 */
public class ZarinpalValidationException extends ZarinpalException {

    /**
     * Creates a validation exception.
     *
     * @param message validation error detail
     */
    public ZarinpalValidationException(String message) {
        super(message);
    }

    /**
     * Creates a validation exception with cause.
     *
     * @param message validation error detail
     * @param cause root cause
     */
    public ZarinpalValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
