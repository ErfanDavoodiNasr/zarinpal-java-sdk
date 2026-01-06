package com.ernoxin.zarinpaljavasdk.exception;

public class ZarinpalException extends RuntimeException {
    public ZarinpalException(String message) {
        super(message);
    }

    public ZarinpalException(String message, Throwable cause) {
        super(message, cause);
    }
}
