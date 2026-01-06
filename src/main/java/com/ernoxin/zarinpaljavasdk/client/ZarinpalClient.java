package com.ernoxin.zarinpaljavasdk.client;

import com.ernoxin.zarinpaljavasdk.callback.ZarinpalCallback;
import com.ernoxin.zarinpaljavasdk.callback.ZarinpalCallbackStatus;
import com.ernoxin.zarinpaljavasdk.config.ZarinpalConfig;
import com.ernoxin.zarinpaljavasdk.exception.ZarinpalCallbackException;
import com.ernoxin.zarinpaljavasdk.exception.ZarinpalValidationException;
import com.ernoxin.zarinpaljavasdk.http.ZarinpalHttpClient;
import com.ernoxin.zarinpaljavasdk.model.*;
import com.ernoxin.zarinpaljavasdk.support.ZarinpalEndpoints;
import com.ernoxin.zarinpaljavasdk.support.ZarinpalValidation;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.*;

public final class ZarinpalClient {
    private final ZarinpalConfig config;
    private final ZarinpalHttpClient httpClient;

    public ZarinpalClient(ZarinpalConfig config) {
        this(config, ZarinpalHttpClient.create(config));
    }

    public ZarinpalClient(ZarinpalConfig config, ZarinpalHttpClient httpClient) {
        if (config == null) {
            throw new ZarinpalValidationException("config is required");
        }
        if (httpClient == null) {
            throw new ZarinpalValidationException("httpClient is required");
        }
        this.config = config;
        this.httpClient = httpClient;
    }

    public PaymentRequestResult requestPayment(PaymentRequest request) {
        if (request == null) {
            throw new ZarinpalValidationException("payment request is required");
        }
        validatePaymentRequest(request);
        URI callbackUrl = request.callbackUrl() != null ? request.callbackUrl() : config.callbackUrl();
        PaymentRequestPayload payload = new PaymentRequestPayload(
                config.merchantId(),
                request.amount(),
                request.currency(),
                request.description(),
                callbackUrl,
                request.referrerId(),
                request.metadata(),
                request.cartData(),
                request.wages()
        );
        return httpClient.post(ZarinpalEndpoints.request(config.operationVersion()), payload, PaymentRequestResult.class, Set.of(100));
    }

    public VerifyResult verifyPayment(VerifyRequest request) {
        if (request == null) {
            throw new ZarinpalValidationException("verify request is required");
        }
        validateVerifyRequest(request);
        VerifyPayload payload = new VerifyPayload(config.merchantId(), request.amount(), request.authority());
        return httpClient.post(ZarinpalEndpoints.verify(config.operationVersion()), payload, VerifyResult.class, Set.of(100, 101));
    }

    public ReverseResult reversePayment(ReverseRequest request) {
        if (request == null) {
            throw new ZarinpalValidationException("reverse request is required");
        }
        ZarinpalValidation.requireAuthority(request.authority());
        ReversePayload payload = new ReversePayload(config.merchantId(), request.authority());
        return httpClient.post(ZarinpalEndpoints.reverse(config.operationVersion()), payload, ReverseResult.class, Set.of(100));
    }

    public UnverifiedResult unverifiedPayments() {
        UnverifiedPayload payload = new UnverifiedPayload(config.merchantId());
        return httpClient.post(ZarinpalEndpoints.unverified(config.operationVersion()), payload, UnverifiedResult.class, Set.of(100));
    }

    public InquiryResult inquirePayment(InquiryRequest request) {
        if (request == null) {
            throw new ZarinpalValidationException("inquiry request is required");
        }
        ZarinpalValidation.requireAuthority(request.authority());
        InquiryPayload payload = new InquiryPayload(config.merchantId(), request.authority());
        return httpClient.post(ZarinpalEndpoints.inquiry(config.operationVersion()), payload, InquiryResult.class, Set.of(100));
    }

    public FeeCalculationResult calculateFee(FeeCalculationRequest request) {
        if (request == null) {
            throw new ZarinpalValidationException("fee calculation request is required");
        }
        validateFeeCalculationRequest(request);
        FeeCalculationPayload payload = new FeeCalculationPayload(config.merchantId(), request.amount(), request.currency());
        return httpClient.post(ZarinpalEndpoints.feeCalculation(config.operationVersion()), payload, FeeCalculationResult.class, Set.of(100));
    }

    public String buildRedirectUrl(String authority) {
        ZarinpalValidation.requireAuthority(authority);
        URI baseUrl = config.baseUrl();
        return baseUrl + ZarinpalEndpoints.startPay() + authority;
    }

    public ZarinpalCallback parseCallback(Map<String, String> queryParams) {
        if (queryParams == null) {
            throw new ZarinpalCallbackException("queryParams is required");
        }
        String authority = findParam(queryParams, "Authority");
        String statusValue = findParam(queryParams, "Status");
        if (statusValue == null || statusValue.isBlank()) {
            throw new ZarinpalCallbackException("Status is required");
        }
        ZarinpalCallbackStatus status;
        try {
            status = ZarinpalCallbackStatus.valueOf(statusValue.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new ZarinpalCallbackException("Status is invalid: " + statusValue);
        }
        if (authority == null || authority.isBlank()) {
            throw new ZarinpalCallbackException("Authority is required");
        }
        ZarinpalValidation.requireAuthority(authority);
        return new ZarinpalCallback(authority, status);
    }

    public ZarinpalCallback parseCallback(MultiValueMap<String, String> queryParams) {
        if (queryParams == null) {
            throw new ZarinpalCallbackException("queryParams is required");
        }
        Map<String, String> flat = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                flat.put(entry.getKey(), entry.getValue().getFirst());
            }
        }
        return parseCallback(flat);
    }

    private void validatePaymentRequest(PaymentRequest request) {
        ZarinpalValidation.requirePositive(request.amount(), "amount");
        ZarinpalValidation.requireNonBlank(request.description(), "description");
        ZarinpalValidation.requireMaxLength(request.description(), 500, "description");
        URI callbackUrl = request.callbackUrl() != null ? request.callbackUrl() : config.callbackUrl();
        if (callbackUrl == null) {
            throw new ZarinpalValidationException("callbackUrl is required");
        }
        ZarinpalValidation.requireHttpUri(callbackUrl, "callbackUrl");
        if (request.referrerId() != null) {
            ZarinpalValidation.requireNonBlank(request.referrerId(), "referrerId");
        }
        validateMetadata(request.metadata());
        validateCartData(request.cartData());
        validateWages(request.wages(), request.amount());
    }

    private void validateMetadata(PaymentMetadata metadata) {
        if (metadata == null) {
            return;
        }
        if (metadata.mobile() != null) {
            ZarinpalValidation.requireNonBlank(metadata.mobile(), "metadata.mobile");
        }
        if (metadata.email() != null) {
            ZarinpalValidation.requireNonBlank(metadata.email(), "metadata.email");
        }
        if (metadata.orderId() != null) {
            ZarinpalValidation.requireNonBlank(metadata.orderId(), "metadata.orderId");
        }
        if (metadata.cardPan() != null) {
            ZarinpalValidation.requireCardPan(metadata.cardPan());
        }
    }

    private void validateVerifyRequest(VerifyRequest request) {
        ZarinpalValidation.requirePositive(request.amount(), "amount");
        ZarinpalValidation.requireAuthority(request.authority());
    }

    private void validateFeeCalculationRequest(FeeCalculationRequest request) {
        ZarinpalValidation.requireMin(request.amount(), 1000, "amount");
    }

    private void validateCartData(CartData cartData) {
        if (cartData == null) {
            return;
        }
        List<CartItem> items = cartData.items();
        if (items == null || items.isEmpty()) {
            throw new ZarinpalValidationException("cartData.items is required");
        }
        for (CartItem item : items) {
            if (item == null) {
                throw new ZarinpalValidationException("cartData.items contains null");
            }
            ZarinpalValidation.requireNonBlank(item.itemName(), "cartData.items.itemName");
            ZarinpalValidation.requirePositive(item.itemAmount(), "cartData.items.itemAmount");
            ZarinpalValidation.requirePositive(item.itemCount(), "cartData.items.itemCount");
            ZarinpalValidation.requirePositive(item.itemAmountSum(), "cartData.items.itemAmountSum");
            long expectedSum;
            try {
                expectedSum = Math.multiplyExact(item.itemAmount(), item.itemCount());
            } catch (ArithmeticException ex) {
                throw new ZarinpalValidationException("cartData.items.itemAmountSum is too large");
            }
            if (item.itemAmountSum() != expectedSum) {
                throw new ZarinpalValidationException("cartData.items.itemAmountSum must equal itemAmount * itemCount");
            }
        }
        CartAddedCosts addedCosts = cartData.addedCosts();
        if (addedCosts != null) {
            ZarinpalValidation.requireNonNegative(addedCosts.tax(), "cartData.addedCosts.tax");
            ZarinpalValidation.requireNonNegative(addedCosts.payment(), "cartData.addedCosts.payment");
            ZarinpalValidation.requireNonNegative(addedCosts.transport(), "cartData.addedCosts.transport");
        }
        CartDeductions deductions = cartData.deductions();
        if (deductions != null) {
            ZarinpalValidation.requireNonNegative(deductions.discount(), "cartData.deductions.discount");
        }
    }

    private void validateWages(List<PaymentWage> wages, long amount) {
        if (wages == null) {
            return;
        }
        if (wages.isEmpty()) {
            throw new ZarinpalValidationException("wages must not be empty");
        }
        if (wages.size() > 5) {
            throw new ZarinpalValidationException("wages size must be at most 5");
        }
        long total = 0;
        for (PaymentWage wage : wages) {
            if (wage == null) {
                throw new ZarinpalValidationException("wages contains null");
            }
            ZarinpalValidation.requireIban(wage.iban());
            ZarinpalValidation.requirePositive(wage.amount(), "wages.amount");
            ZarinpalValidation.requireNonBlank(wage.description(), "wages.description");
            try {
                total = Math.addExact(total, wage.amount());
            } catch (ArithmeticException ex) {
                throw new ZarinpalValidationException("wages total is too large");
            }
        }
        long maxAllowed = amount - Math.floorDiv(amount, 100);
        if (total > maxAllowed) {
            throw new ZarinpalValidationException("wages total must be at most 99% of amount");
        }
    }

    private String findParam(Map<String, String> params, String name) {
        if (params.containsKey(name)) {
            return params.get(name);
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey() != null && entry.getKey().equalsIgnoreCase(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private record PaymentRequestPayload(
            String merchantId,
            long amount,
            ZarinpalCurrency currency,
            String description,
            URI callbackUrl,
            String referrerId,
            PaymentMetadata metadata,
            CartData cartData,
            List<PaymentWage> wages
    ) {
    }

    private record VerifyPayload(String merchantId, long amount, String authority) {
    }

    private record ReversePayload(String merchantId, String authority) {
    }

    private record UnverifiedPayload(String merchantId) {
    }

    private record InquiryPayload(String merchantId, String authority) {
    }

    private record FeeCalculationPayload(String merchantId, long amount, ZarinpalCurrency currency) {
    }
}
