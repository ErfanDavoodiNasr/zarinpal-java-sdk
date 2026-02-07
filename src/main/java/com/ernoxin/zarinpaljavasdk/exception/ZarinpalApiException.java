package com.ernoxin.zarinpaljavasdk.exception;

import lombok.Getter;

/**
 * Thrown when Zarinpal returns an unsuccessful API response.
 *
 * <p>Includes both HTTP status and gateway-specific code/message when available.
 *
 */
@Getter
public class ZarinpalApiException extends ZarinpalException {
    private final int httpStatus;
    private final Integer gatewayCode;
    private final String gatewayMessage;
    private final String rawBody;

    /**
     * Creates an API exception.
     *
     * @param httpStatus HTTP response status code
     * @param gatewayCode gateway-level code extracted from response, if available
     * @param gatewayMessage gateway-level message or resolved catalog message
     * @param rawBody raw response body for diagnostics
     */
    public ZarinpalApiException(int httpStatus, Integer gatewayCode, String gatewayMessage, String rawBody) {
        super(buildMessage(httpStatus, gatewayCode, gatewayMessage));
        this.httpStatus = httpStatus;
        this.gatewayCode = gatewayCode;
        this.gatewayMessage = gatewayMessage;
        this.rawBody = rawBody;
    }

    /**
     * Creates an API exception with cause.
     *
     * @param httpStatus HTTP response status code
     * @param gatewayCode gateway-level code extracted from response, if available
     * @param gatewayMessage gateway-level message or resolved catalog message
     * @param rawBody raw response body for diagnostics
     * @param cause root cause (for example JSON parsing failure)
     */
    public ZarinpalApiException(int httpStatus, Integer gatewayCode, String gatewayMessage, String rawBody, Throwable cause) {
        super(buildMessage(httpStatus, gatewayCode, gatewayMessage), cause);
        this.httpStatus = httpStatus;
        this.gatewayCode = gatewayCode;
        this.gatewayMessage = gatewayMessage;
        this.rawBody = rawBody;
    }

    private static String buildMessage(int httpStatus, Integer gatewayCode, String gatewayMessage) {
        StringBuilder builder = new StringBuilder();
        builder.append("Zarinpal API error");
        builder.append(" (http ").append(httpStatus).append(")");
        if (gatewayCode != null) {
            builder.append(" code ").append(gatewayCode);
        }
        if (gatewayMessage != null && !gatewayMessage.isBlank()) {
            builder.append(": ").append(gatewayMessage);
        }
        return builder.toString();
    }
}
