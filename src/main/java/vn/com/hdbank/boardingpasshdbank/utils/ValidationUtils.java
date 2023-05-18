package vn.com.hdbank.boardingpasshdbank.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtils.class);

    private ValidationUtils() {}

    public static Map<String, String> validationHandler(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            LOGGER.error("Validation errors: {}", errors);
        }
        return errors;
    }

}
