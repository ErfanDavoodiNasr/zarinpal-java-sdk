package com.ernoxin.zarinpaljavasdk.model;

/**
 * Result returned by reverse operation.
 *
 * @param code gateway response code
 * @param message gateway response message
 */
public record ReverseResult(
        int code,
        String message
) {
}
