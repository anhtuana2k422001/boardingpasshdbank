package vn.com.hdbank.boardingpasshdbank.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import vn.com.hdbank.boardingpasshdbank.common.Constant;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ValidationUtils {

    public static Map<String, String> validationHandler(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            LOGGER.error(Constant.ERROR_VALIDATE, errors);
        }
        return errors;
    }

}
