/**
 * Core API for interacting with the Zarinpal payment gateway.
 *
 * <p>The main entry point is {@link com.ernoxin.zarinpaljavasdk.client.ZarinpalClient},
 * which provides strongly typed operations for payment request, verification,
 * reverse, inquiry, and callback parsing.
 *
 * <p>Typical flow:
 * <ol>
 *   <li>Create a {@link com.ernoxin.zarinpaljavasdk.config.ZarinpalConfig}.</li>
 *   <li>Send a payment request by calling
 *       {@link com.ernoxin.zarinpaljavasdk.client.ZarinpalClient#requestPayment(com.ernoxin.zarinpaljavasdk.model.PaymentRequest)}.</li>
 *   <li>Redirect the user using
 *       {@link com.ernoxin.zarinpaljavasdk.client.ZarinpalClient#buildRedirectUrl(String)}.</li>
 *   <li>Parse callback parameters with
 *       {@link com.ernoxin.zarinpaljavasdk.client.ZarinpalClient#parseCallback(java.util.Map)}.</li>
 *   <li>Verify successful callbacks with
 *       {@link com.ernoxin.zarinpaljavasdk.client.ZarinpalClient#verifyPayment(com.ernoxin.zarinpaljavasdk.model.VerifyRequest)}.</li>
 * </ol>
 *
 * <p>Amount units depend on {@link com.ernoxin.zarinpaljavasdk.model.ZarinpalCurrency}:
 * {@code IRR} (rial) or {@code IRT} (toman). Configure amount limits in
 * {@link com.ernoxin.zarinpaljavasdk.config.ZarinpalConfig}.
 *
 */
package com.ernoxin.zarinpaljavasdk;