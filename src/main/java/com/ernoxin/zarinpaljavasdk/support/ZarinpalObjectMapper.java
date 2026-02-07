package com.ernoxin.zarinpaljavasdk.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.experimental.UtilityClass;

/**
 * Factory for the SDK's default Jackson {@link ObjectMapper}.
 *
 * <p>Configured with snake_case naming, null omission, tolerant unknown-property
 * handling, and case-insensitive enum deserialization.
 *
 */
@UtilityClass
public class ZarinpalObjectMapper {

    /**
     * Creates a new mapper instance with SDK defaults.
     *
     * @return configured mapper
     */
    public static ObjectMapper create() {
        return JsonMapper.builder()
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
                .build();
    }
}
