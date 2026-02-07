package com.ernoxin.zarinpaljavasdk.http;

import com.ernoxin.zarinpaljavasdk.config.ZarinpalConfig;
import com.ernoxin.zarinpaljavasdk.exception.ZarinpalTransportException;
import com.ernoxin.zarinpaljavasdk.exception.ZarinpalValidationException;
import com.ernoxin.zarinpaljavasdk.support.ZarinpalObjectMapper;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

/**
 * Low-level HTTP client wrapper for Zarinpal API calls.
 *
 * <p>This component serializes request payloads, configures headers, executes
 * POST requests, applies optional transport retries, and delegates response
 * validation to {@link ZarinpalResponseParser}.
 *
 * <p>Thread-safety: safe for concurrent use once fully constructed.
 *
 */
public final class ZarinpalHttpClient {
    private final ZarinpalConfig config;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final ZarinpalResponseParser responseParser;

    /**
     * Creates an HTTP client using caller-provided dependencies.
     *
     * @param config SDK config containing base URL, timeouts, retries, and user agent
     * @param restTemplate transport client implementation
     * @param mapper JSON mapper used for request serialization and response parsing
     */
    public ZarinpalHttpClient(ZarinpalConfig config, RestTemplate restTemplate, ObjectMapper mapper) {
        this.config = config;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.responseParser = new ZarinpalResponseParser(mapper);
        configureRestTemplate(restTemplate, config);
    }

    /**
     * Creates a default HTTP client from SDK configuration.
     *
     * @param config SDK config
     * @return new HTTP client instance
     */
    public static ZarinpalHttpClient create(ZarinpalConfig config) {
        ObjectMapper mapper = ZarinpalObjectMapper.create();
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(config.connectTimeout())
                .build();
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return new ZarinpalHttpClient(config, restTemplate, mapper);
    }

    private static void configureRestTemplate(RestTemplate restTemplate, ZarinpalConfig config) {
        ClientHttpRequestFactory requestFactory = restTemplate.getRequestFactory();
        if (requestFactory instanceof SimpleClientHttpRequestFactory simpleFactory) {
            simpleFactory.setConnectTimeout((int) config.connectTimeout().toMillis());
            simpleFactory.setReadTimeout((int) config.readTimeout().toMillis());
        } else if (requestFactory instanceof JdkClientHttpRequestFactory jdkFactory) {
            jdkFactory.setReadTimeout(config.readTimeout());
        }
        if (restTemplate.getErrorHandler() instanceof DefaultResponseErrorHandler) {
            restTemplate.setErrorHandler(new ResponseErrorHandler() {
                @Override
                public boolean hasError(ClientHttpResponse response) {
                    return false;
                }

                @Override
                public void handleError(URI url, HttpMethod method, ClientHttpResponse response) {
                }
            });
        }
    }

    /**
     * Executes a POST call and maps the response data section into {@code dataType}.
     *
     * <p>Retries are applied only to transport exceptions when retry is enabled in
     * {@link ZarinpalConfig}. Logical API errors are not retried.
     *
     * @param path endpoint path relative to configured base URL
     * @param request request payload object
     * @param dataType target data type for {@code data} JSON object
     * @param successCodes acceptable gateway codes in {@code data.code}
     * @param <T> response type
     * @return parsed and validated response data object
     * @throws ZarinpalValidationException when request body serialization fails
     * @throws ZarinpalTransportException when transport fails or no response is received
     */
    public <T> T post(String path, Object request, Class<T> dataType, Set<Integer> successCodes) {
        URI baseUrl = config.baseUrl();
        URI url = UriComponentsBuilder.fromUri(baseUrl).path(path).build().toUri();
        String body = writeBody(request);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.USER_AGENT, config.userAgent());
        int attempts = config.retryEnabled() ? config.retryMaxAttempts() : 1;
        long backoffMillis = config.retryEnabled() ? config.retryBackoff().toMillis() : 0;
        RestClientException last = null;
        for (int attempt = 1; attempt <= attempts; attempt++) {
            ResponseEntity<String> response;
            try {
                response = restTemplate.execute(url, HttpMethod.POST, httpRequest -> {
                    httpRequest.getHeaders().putAll(headers);
                    if (body != null && !body.isBlank()) {
                        httpRequest.getBody().write(body.getBytes(StandardCharsets.UTF_8));
                    }
                }, responseExtractor);
            } catch (RestClientException ex) {
                last = ex;
                if (attempt == attempts) {
                    throw new ZarinpalTransportException("Request to Zarinpal failed", ex);
                }
                if (backoffMillis > 0) {
                    try {
                        Thread.sleep(backoffMillis);
                    } catch (InterruptedException interrupted) {
                        Thread.currentThread().interrupt();
                        throw new ZarinpalTransportException("Request to Zarinpal failed", interrupted);
                    }
                }
                continue;
            }
            if (response == null) {
                throw new ZarinpalTransportException("Request to Zarinpal failed", null);
            }
            return responseParser.parse(response, successCodes, dataType);
        }
        throw new ZarinpalTransportException("Request to Zarinpal failed", last);
    }

    private String writeBody(Object request) {
        try {
            return mapper.writeValueAsString(request);
        } catch (JacksonException ex) {
            throw new ZarinpalValidationException("Request body is invalid", ex);
        }
    }

    private static final ResponseExtractor<ResponseEntity<String>> responseExtractor = response -> {
        String responseBody = null;
        try (InputStream stream = response.getBody()) {
            if (stream != null) {
                responseBody = StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
            }
        }
        return new ResponseEntity<>(responseBody, response.getHeaders(), response.getStatusCode());
    };
}
