package com.ernoxin.zarinpaljavasdk.exception;

public class ZarinpalValidationException extends ZarinpalException {
    public ZarinpalValidationException(String message) {
        super(message);
    }

    public ZarinpalValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
