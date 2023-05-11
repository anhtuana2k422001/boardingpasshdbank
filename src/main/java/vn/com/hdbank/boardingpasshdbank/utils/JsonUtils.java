package vn.com.hdbank.boardingpasshdbank.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
    }

    public static String toJsonString(Object object) {
        if (object == null) {
            return StringUtils.EMPTY;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e){
            //log
            return StringUtils.EMPTY;
        }
    }


    public static  <T> T fromJsonString(String jsonString, Class<T> valueType) throws IOException {
        return objectMapper.readValue(jsonString, valueType);
    }
}