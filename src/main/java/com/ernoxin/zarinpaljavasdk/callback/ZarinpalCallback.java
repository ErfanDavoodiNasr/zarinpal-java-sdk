package com.ernoxin.zarinpaljavasdk.callback;

public record ZarinpalCallback(String authority, ZarinpalCallbackStatus status) {
    public boolean isOk() {
        return status == ZarinpalCallbackStatus.OK;
    }
}
