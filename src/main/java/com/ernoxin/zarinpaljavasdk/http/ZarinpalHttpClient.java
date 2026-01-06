package com.ernoxin.zarinpaljavasdk.http;

import com.ernoxin.zarinpaljavasdk.config.ZarinpalConfig;
import com.ernoxin.zarinpaljavasdk.exception.ZarinpalTransportException;
import com.ernoxin.zarinpaljavasdk.exception.ZarinpalValidationException;
import com.ernoxin.zarinpaljavasdk.support.ZarinpalObjectMapper;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.util.List;
import java.util.Set;

public final class ZarinpalHttpClient {
    private final ZarinpalConfig config;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final ZarinpalResponseParser responseParser;

    public ZarinpalHttpClient(ZarinpalConfig config, RestTemplate restTemplate, ObjectMapper mapper) {
        this.config = config;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.responseParser = new ZarinpalResponseParser(mapper);
        configureRestTemplate(restTemplate, config);
    }

    public static ZarinpalHttpClient create(ZarinpalConfig config) {
        ObjectMapper mapper = ZarinpalObjectMapper.create();
        RestTemplate restTemplate = new RestTemplate();
        configureRestTemplate(restTemplate, config);
        return new ZarinpalHttpClient(config, restTemplate, mapper);
    }

    private static void configureRestTemplate(RestTemplate restTemplate, ZarinpalConfig config) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout((int) config.connectTimeout().toMillis());
        requestFactory.setReadTimeout((int) config.readTimeout().toMillis());
        restTemplate.setRequestFactory(requestFactory);
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

    public <T> T post(String path, Object request, Class<T> dataType, Set<Integer> successCodes) {
        URI baseUrl = config.baseUrl();
        String url = baseUrl + path;
        String body = writeBody(request);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.USER_AGENT, config.userAgent());
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        int attempts = config.retryEnabled() ? config.retryMaxAttempts() : 1;
        long backoffMillis = config.retryEnabled() ? config.retryBackoff().toMillis() : 0;
        RestClientException last = null;
        for (int attempt = 1; attempt <= attempts; attempt++) {
            ResponseEntity<String> response;
            try {
                response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
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
            return responseParser.parse(response, successCodes, dataType);
        }
        throw new ZarinpalTransportException("Request to Zarinpal failed", last);
    }

    private String writeBody(Object request) {
        try {
            return mapper.writeValueAsString(request);
        } catch (JacksonException ex) {
            throw new ZarinpalValidationException("Request body is invalid");
        }
    }
}
