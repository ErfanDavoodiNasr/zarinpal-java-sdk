package com.ernoxin.zarinpaljavasdk.model;

/**
 * Item entry in cart data payload.
 *
 * @param itemName item name (required)
 * @param itemAmount amount per item unit, must be positive
 * @param itemCount item count, must be positive
 * @param itemAmountSum total amount for this row, must equal {@code itemAmount * itemCount}
 */
public record CartItem(
        String itemName,
        long itemAmount,
        long itemCount,
        long itemAmountSum
) {
}
