package com.ernoxin.zarinpaljavasdk.http;

import com.ernoxin.zarinpaljavasdk.exception.ZarinpalApiException;
import com.ernoxin.zarinpaljavasdk.support.ZarinpalErrorCatalog;
import org.springframework.http.ResponseEntity;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Set;

public final class ZarinpalResponseParser {
    private final ObjectMapper mapper;

    public ZarinpalResponseParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T parse(ResponseEntity<String> response, Set<Integer> successCodes, Class<T> dataType) {
        int status = response.getStatusCode().value();
        String body = response.getBody();
        if (body == null || body.isBlank()) {
            throw new ZarinpalApiException(status, null, "Empty response body", body);
        }
        JsonNode root = readTree(status, body);
        JsonNode errorsNode = root.path("errors");
        Integer errorCode = extractErrorCode(errorsNode);
        String errorMessage = extractErrorMessage(root, errorsNode);
        if (hasErrors(errorsNode)) {
            throw new ZarinpalApiException(status, errorCode, resolveMessage(errorCode, errorMessage), body);
        }
        JsonNode dataNode = root.path("data");
        if (dataNode.isMissingNode() || dataNode.isNull() || ((dataNode.isObject() || dataNode.isArray()) && dataNode.isEmpty())) {
            String message = errorMessage != null ? errorMessage : "Missing data in response";
            throw new ZarinpalApiException(status, errorCode, resolveMessage(errorCode, message), body);
        }
        Integer dataCode = extractCode(dataNode.get("code"));
        String dataMessage = textOrNull(dataNode.get("message"));
        if (dataCode == null || !successCodes.contains(dataCode)) {
            String message = dataMessage != null ? dataMessage : errorMessage;
            throw new ZarinpalApiException(status, dataCode, resolveMessage(dataCode, message), body);
        }
        if (!response.getStatusCode().is2xxSuccessful()) {
            String message = dataMessage != null ? dataMessage : errorMessage;
            throw new ZarinpalApiException(status, dataCode, resolveMessage(dataCode, message), body);
        }
        try {
            return mapper.treeToValue(dataNode, dataType);
        } catch (JacksonException ex) {
            throw new ZarinpalApiException(status, dataCode, "Invalid response body", body, ex);
        }
    }

    private JsonNode readTree(int status, String body) {
        try {
            return mapper.readTree(body);
        } catch (JacksonException ex) {
            throw new ZarinpalApiException(status, null, "Invalid JSON response", body, ex);
        }
    }

    private boolean hasErrors(JsonNode errorsNode) {
        if (errorsNode == null || errorsNode.isMissingNode() || errorsNode.isNull()) {
            return false;
        }
        if (errorsNode.isArray()) {
            return !errorsNode.isEmpty();
        }
        if (errorsNode.isObject()) {
            return !errorsNode.isEmpty();
        }
        return false;
    }

    private Integer extractErrorCode(JsonNode errorsNode) {
        if (errorsNode == null || errorsNode.isMissingNode() || errorsNode.isNull()) {
            return null;
        }
        JsonNode directCode = errorsNode.get("code");
        Integer direct = extractCode(directCode);
        if (direct != null) {
            return direct;
        }
        return findNumeric(errorsNode);
    }

    private Integer extractCode(JsonNode node) {
        if (node == null || node.isNull() || node.isMissingNode()) {
            return null;
        }
        if (node.isInt() || node.isLong()) {
            return node.asInt();
        }
        if (node.isString()) {
            String text = node.asString();
            if (text != null) {
                String trimmed = text.trim();
                try {
                    return Integer.parseInt(trimmed);
                } catch (NumberFormatException ex) {
                    return null;
                }
            }
        }
        return null;
    }

    private Integer findNumeric(JsonNode node) {
        if (node == null || node.isNull() || node.isMissingNode()) {
            return null;
        }
        Integer direct = extractCode(node);
        if (direct != null) {
            return direct;
        }
        if (node.isArray()) {
            for (JsonNode item : node) {
                Integer found = findNumeric(item);
                if (found != null) {
                    return found;
                }
            }
        }
        if (node.isObject()) {
            for (JsonNode jsonNode : node) {
                Integer found = findNumeric(jsonNode);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private String extractErrorMessage(JsonNode root, JsonNode errorsNode) {
        String rootMessage = textOrNull(root.get("message"));
        if (rootMessage != null) {
            return rootMessage;
        }
        if (errorsNode == null || errorsNode.isMissingNode() || errorsNode.isNull()) {
            return null;
        }
        String errorsMessage = textOrNull(errorsNode.get("message"));
        if (errorsMessage != null) {
            return errorsMessage;
        }
        return null;
    }

    private String textOrNull(JsonNode node) {
        if (node == null || node.isNull() || node.isMissingNode()) {
            return null;
        }
        if (node.isString()) {
            String value = node.asString();
            return value != null && !value.isBlank() ? value : null;
        }
        return null;
    }

    private String resolveMessage(Integer code, String fallback) {
        String catalogMessage = ZarinpalErrorCatalog.messageFor(code);
        if (catalogMessage != null && !catalogMessage.isBlank()) {
            return catalogMessage;
        }
        if (fallback != null && !fallback.isBlank()) {
            return fallback;
        }
        return "Zarinpal API error";
    }
}
