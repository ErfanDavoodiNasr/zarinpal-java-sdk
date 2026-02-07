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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

/**
 * High-level synchronous client for Zarinpal payment operations.
 *
 * <p>Main responsibilities:
 * <ol>
 *   <li>Validate request models before network calls.</li>
 *   <li>Map API operations to endpoint paths and payloads.</li>
 *   <li>Parse callback query parameters to a typed model.</li>
 * </ol>
 *
 * <p>Thread-safety: this type is immutable and safe for concurrent use when
 * shared across threads.
 *
 * <p>Network behavior is controlled by {@link ZarinpalConfig}:
 * connect/read timeouts, retry enablement, max attempts, and retry backoff.
 *
 * <p>Example payment and verification flow:
 * <pre>{@code
 * PaymentRequest request = PaymentRequest.builder(150_000, "Subscription")
 *         .currency(ZarinpalCurrency.IRR)
 *         .build();
 *
 * PaymentRequestResult created = client.requestPayment(request);
 * String redirectUrl = client.buildRedirectUrl(created.authority());
 *
 * ZarinpalCallback callback = client.parseCallback(queryParams);
 * if (callback.isOk()) {
 *     VerifyResult verified = client.verifyPayment(new VerifyRequest(150_000, callback.authority()));
 * }
 * }</pre>
 *
 */
public final class ZarinpalClient {
    private final ZarinpalConfig config;
    private final ZarinpalHttpClient httpClient;

    /**
     * Creates a client using the SDK default HTTP client.
     *
     * @param config validated SDK configuration
     * @throws ZarinpalValidationException when {@code config} is {@code null}
     */
    public ZarinpalClient(ZarinpalConfig config) {
        this(config, ZarinpalHttpClient.create(config));
    }

    /**
     * Creates a client with a custom HTTP client.
     *
     * @param config validated SDK configuration
     * @param httpClient HTTP client implementation
     * @throws ZarinpalValidationException when any argument is {@code null}
     */
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

    /**
     * Requests a new payment authority.
     *
     * <p>Callback URL resolution order:
     * <ol>
     *   <li>{@link PaymentRequest#callbackUrl()}</li>
     *   <li>{@link ZarinpalConfig#callbackUrl()}</li>
     * </ol>
     *
     * @param request payment request model
     * @return typed creation result containing authority and gateway response data
     * @throws ZarinpalValidationException when request is null or fails local validation
     */
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

    /**
     * Verifies a previously created payment authority.
     *
     * @param request verification request containing original amount and authority
     * @return verification result
     * @throws ZarinpalValidationException when request is null or invalid
     */
    public VerifyResult verifyPayment(VerifyRequest request) {
        if (request == null) {
            throw new ZarinpalValidationException("verify request is required");
        }
        validateVerifyRequest(request);
        VerifyPayload payload = new VerifyPayload(config.merchantId(), request.amount(), request.authority());
        return httpClient.post(ZarinpalEndpoints.verify(config.operationVersion()), payload, VerifyResult.class, Set.of(100, 101));
    }

    /**
     * Reverses a verified payment when gateway rules allow it.
     *
     * @param request reverse request containing authority
     * @return reverse result
     * @throws ZarinpalValidationException when request is null or authority is invalid
     */
    public ReverseResult reversePayment(ReverseRequest request) {
        if (request == null) {
            throw new ZarinpalValidationException("reverse request is required");
        }
        ZarinpalValidation.requireAuthority(request.authority());
        ReversePayload payload = new ReversePayload(config.merchantId(), request.authority());
        return httpClient.post(ZarinpalEndpoints.reverse(config.operationVersion()), payload, ReverseResult.class, Set.of(100));
    }

    /**
     * Returns unverified payment authorities for the configured merchant.
     *
     * @return unverified authorities and gateway response metadata
     */
    public UnverifiedResult unverifiedPayments() {
        UnverifiedPayload payload = new UnverifiedPayload(config.merchantId());
        return httpClient.post(ZarinpalEndpoints.unverified(config.operationVersion()), payload, UnverifiedResult.class, Set.of(100));
    }

    /**
     * Retrieves current status for a payment authority.
     *
     * @param request inquiry request containing authority
     * @return inquiry result
     * @throws ZarinpalValidationException when request is null or authority is invalid
     */
    public InquiryResult inquirePayment(InquiryRequest request) {
        if (request == null) {
            throw new ZarinpalValidationException("inquiry request is required");
        }
        ZarinpalValidation.requireAuthority(request.authority());
        InquiryPayload payload = new InquiryPayload(config.merchantId(), request.authority());
        return httpClient.post(ZarinpalEndpoints.inquiry(config.operationVersion()), payload, InquiryResult.class, Set.of(100));
    }

    /**
     * Calculates gateway fee for a candidate amount.
     *
     * @param request fee calculation request
     * @return fee calculation result
     * @throws ZarinpalValidationException when request is null or amount is below minimum
     */
    public FeeCalculationResult calculateFee(FeeCalculationRequest request) {
        if (request == null) {
            throw new ZarinpalValidationException("fee calculation request is required");
        }
        validateFeeCalculationRequest(request);
        FeeCalculationPayload payload = new FeeCalculationPayload(config.merchantId(), request.amount(), request.currency());
        return httpClient.post(ZarinpalEndpoints.feeCalculation(config.operationVersion()), payload, FeeCalculationResult.class, Set.of(100));
    }

    /**
     * Builds redirect URL for sending user to Zarinpal payment page.
     *
     * @param authority authority returned by payment request
     * @return absolute redirect URL
     * @throws ZarinpalValidationException when authority is invalid
     */
    public String buildRedirectUrl(String authority) {
        ZarinpalValidation.requireAuthority(authority);
        URI baseUrl = config.baseUrl();
        return UriComponentsBuilder.fromUri(baseUrl)
                .path(ZarinpalEndpoints.startPay())
                .path(authority)
                .build()
                .toUriString();
    }

    /**
     * Parses callback query parameters from a flat map.
     *
     * <p>Parameter names are matched case-insensitively for {@code Authority}
     * and {@code Status}. Valid status values are {@code OK} and {@code NOK}.
     *
     * @param queryParams callback query parameter map
     * @return parsed callback model
     * @throws ZarinpalCallbackException when required parameters are missing or invalid
     */
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

    /**
     * Parses callback query parameters from Spring's {@link MultiValueMap}.
     *
     * <p>Only the first value of each key is considered.
     *
     * @param queryParams callback query parameters
     * @return parsed callback model
     * @throws ZarinpalCallbackException when required parameters are missing or invalid
     */
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
        long maxAmount = request.currency() == ZarinpalCurrency.IRT ? config.maxAmountIrt() : config.maxAmountIrr();
        if (request.amount() > maxAmount) {
            throw new ZarinpalValidationException("amount must be at most " + maxAmount);
        }
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
            if (wage.amount() < config.minWageAmount()) {
                throw new ZarinpalValidationException("wages.amount must be at least " + config.minWageAmount());
            }
            ZarinpalValidation.requireNonBlank(wage.description(), "wages.description");
            try {
                total = Math.addExact(total, wage.amount());
            } catch (ArithmeticException ex) {
                throw new ZarinpalValidationException("wages total is too large");
            }
        }

        // Gateway expects total wages to remain under or equal to 99% of original amount.
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
