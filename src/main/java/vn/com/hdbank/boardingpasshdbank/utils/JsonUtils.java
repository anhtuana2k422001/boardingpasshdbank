package vn.com.hdbank.boardingpasshdbank.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonUtils {
    private static final Logger LOGGER  = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private JsonUtils() {}

    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e){
            LOGGER.error("Error to Json String: ", e);
            return StringUtils.EMPTY;
        }
    }

    public static <T> T fromJsonString(String jsonString, Class<T> valueType) {
        try {
            return objectMapper.readValue(jsonString, valueType);
        } catch (Exception e){
            LOGGER.error("Error to Json String: ", e);
            throw new IllegalStateException("Error to Json String");
        }
    }
}