package com.ernoxin.zarinpaljavasdk.model;

import java.net.URI;
import java.util.List;

/**
 * Payment request payload.
 *
 * <p>Amounts are interpreted by {@link #currency()}:
 * {@link ZarinpalCurrency#IRR} for rial or {@link ZarinpalCurrency#IRT} for toman.
 *
 * @param amount payment amount (must be positive and within configured max)
 * @param description payment description (required, max 500 chars)
 * @param callbackUrl optional request-level callback URL; falls back to config callback URL
 * @param currency optional currency unit; when {@code null}, gateway default behavior applies
 * @param referrerId optional referrer code (non-blank when provided)
 * @param metadata optional request metadata
 * @param cartData optional cart data
 * @param wages optional split wages list, max 5 items when provided
 */
public record PaymentRequest(
        long amount,
        String description,
        URI callbackUrl,
        ZarinpalCurrency currency,
        String referrerId,
        PaymentMetadata metadata,
        CartData cartData,
        List<PaymentWage> wages
) {

    /**
     * Creates a builder with mandatory fields.
     *
     * @param amount payment amount
     * @param description payment description
     * @return mutable builder
     */
    public static Builder builder(long amount, String description) {
        return new Builder(amount, description);
    }

    /**
     * Mutable builder for {@link PaymentRequest}.
     */
    public static final class Builder {
        private final long amount;
        private final String description;
        private URI callbackUrl;
        private ZarinpalCurrency currency;
        private String referrerId;
        private PaymentMetadata metadata;
        private CartData cartData;
        private List<PaymentWage> wages;

        private Builder(long amount, String description) {
            this.amount = amount;
            this.description = description;
        }

        /**
         * Sets request-level callback URL.
         *
         * @param callbackUrl callback URL
         * @return this builder
         */
        public Builder callbackUrl(URI callbackUrl) {
            this.callbackUrl = callbackUrl;
            return this;
        }

        /**
         * Sets payment currency.
         *
         * @param currency payment currency
         * @return this builder
         */
        public Builder currency(ZarinpalCurrency currency) {
            this.currency = currency;
            return this;
        }

        /**
         * Sets optional referrer code.
         *
         * @param referrerId referrer code
         * @return this builder
         */
        public Builder referrerId(String referrerId) {
            this.referrerId = referrerId;
            return this;
        }

        /**
         * Sets optional payment metadata.
         *
         * @param metadata metadata object
         * @return this builder
         */
        public Builder metadata(PaymentMetadata metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Sets optional cart data.
         *
         * @param cartData cart data payload
         * @return this builder
         */
        public Builder cartData(CartData cartData) {
            this.cartData = cartData;
            return this;
        }

        /**
         * Sets optional wages list.
         *
         * @param wages wage entries
         * @return this builder
         */
        public Builder wages(List<PaymentWage> wages) {
            this.wages = wages;
            return this;
        }

        /**
         * Builds a new immutable request object.
         *
         * @return payment request
         */
        public PaymentRequest build() {
            return new PaymentRequest(
                    amount,
                    description,
                    callbackUrl,
                    currency,
                    referrerId,
                    metadata,
                    cartData,
                    wages
            );
        }
    }
}
