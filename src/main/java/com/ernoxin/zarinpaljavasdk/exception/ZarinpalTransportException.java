package com.ernoxin.zarinpaljavasdk.exception;

/**
 * Thrown for transport-level failures such as I/O errors or timeouts.
 *
 */
public class ZarinpalTransportException extends ZarinpalException {

    /**
     * Creates a transport exception with cause.
     *
     * @param message transport failure detail
     * @param cause root cause
     */
    public ZarinpalTransportException(String message, Throwable cause) {
        super(message, cause);
    }
}
