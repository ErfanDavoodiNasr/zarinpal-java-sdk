package com.ernoxin.zarinpaljavasdk.model;

import java.util.List;

/**
 * Optional cart payload attached to a payment request.
 *
 * @param items cart items list; required and non-empty when cart data is supplied
 * @param addedCosts optional extra costs
 * @param deductions optional deductions
 */
public record CartData(
        List<CartItem> items,
        CartAddedCosts addedCosts,
        CartDeductions deductions
) {
}
