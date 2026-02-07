package com.ernoxin.zarinpaljavasdk.exception;

/**
 * Base runtime exception for all SDK failures.
 *
 */
public class ZarinpalException extends RuntimeException {

    /**
     * Creates a new exception with message.
     *
     * @param message error description
     */
    public ZarinpalException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with message and cause.
     *
     * @param message error description
     * @param cause root cause
     */
    public ZarinpalException(String message, Throwable cause) {
        super(message, cause);
    }
}
