package com.ernoxin.zarinpaljavasdk.model;

/**
 * Optional extra cost fields for cart payload.
 *
 * @param tax tax amount, non-negative when provided
 * @param payment payment/service extra cost, non-negative when provided
 * @param transport shipping/transport cost, non-negative when provided
 */
public record CartAddedCosts(
        Long tax,
        Long payment,
        Long transport
) {
}
