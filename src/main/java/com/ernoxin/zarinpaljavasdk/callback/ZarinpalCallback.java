package com.ernoxin.zarinpaljavasdk.callback;

/**
 * Parsed callback payload extracted from query parameters.
 *
 * @param authority payment authority identifier
 * @param status callback status
 */
public record ZarinpalCallback(String authority, ZarinpalCallbackStatus status) {

    /**
     * Returns whether callback status is {@link ZarinpalCallbackStatus#OK}.
     *
     * @return {@code true} when verification should be attempted
     */
    public boolean isOk() {
        return status == ZarinpalCallbackStatus.OK;
    }
}
