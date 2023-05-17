package vn.com.hdbank.boardingpasshdbank.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtils.class);
    private ValidationUtils() {}

    public static void handleValidationErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                String errorMessage = String.format("%s: %s", error.getField(), error.getDefaultMessage());
                errors.add(errorMessage);
            }
            LOGGER.error("Validation errors: {}", errors);
            throw new CustomException(ApiResponseStatus.BAD_REQUEST);
        }
    }
}
