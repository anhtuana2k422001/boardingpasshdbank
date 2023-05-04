package vn.com.hdbank.boardingpasshdbank.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
    private static JsonUtils instance;
    private final ObjectMapper objectMapper;

    private JsonUtils() {
        this.objectMapper = new ObjectMapper();
    }

    public static synchronized JsonUtils getInstance() {
        if (instance == null) {
            instance = new JsonUtils();
        }
        return instance;
    }

    public String toJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public <T> T fromJsonString(String jsonString, Class<T> valueType) throws IOException {
        return objectMapper.readValue(jsonString, valueType);
    }
}