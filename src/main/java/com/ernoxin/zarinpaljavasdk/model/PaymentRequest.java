package com.ernoxin.zarinpaljavasdk.model;

import java.net.URI;
import java.util.List;

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
    public static Builder builder(long amount, String description) {
        return new Builder(amount, description);
    }

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

        public Builder callbackUrl(URI callbackUrl) {
            this.callbackUrl = callbackUrl;
            return this;
        }

        public Builder currency(ZarinpalCurrency currency) {
            this.currency = currency;
            return this;
        }

        public Builder referrerId(String referrerId) {
            this.referrerId = referrerId;
            return this;
        }

        public Builder metadata(PaymentMetadata metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder cartData(CartData cartData) {
            this.cartData = cartData;
            return this;
        }

        public Builder wages(List<PaymentWage> wages) {
            this.wages = wages;
            return this;
        }

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
