package com.ernoxin.zarinpaljavasdk.model;

public record CartItem(
        String itemName,
        long itemAmount,
        long itemCount,
        long itemAmountSum
) {
}
