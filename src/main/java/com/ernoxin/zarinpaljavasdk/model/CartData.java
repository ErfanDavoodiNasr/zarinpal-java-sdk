package com.ernoxin.zarinpaljavasdk.model;

import java.util.List;

public record CartData(
        List<CartItem> items,
        CartAddedCosts addedCosts,
        CartDeductions deductions
) {
}
