package com.ernoxin.zarinpaljavasdk.model;

/**
 * Optional deduction fields for cart payload.
 *
 * @param discount discount amount, non-negative when provided
 */
public record CartDeductions(Long discount) {
}
