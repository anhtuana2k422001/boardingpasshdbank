package vn.com.hdbank.boardingpasshdbank.model.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import vn.com.hdbank.boardingpasshdbank.common.Validate;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.BaseRequest;

@Getter
@Setter
public class AuthenticationRequest extends BaseRequest {
    @NotNull(message = Validate.MESSAGE_NOT_NULL)
    @Pattern(regexp = Validate.REGEX_PHONE_NUMBER, message = Validate.MESSAGE_PHONE_NUMBER)
    private String  phoneNumber;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @Pattern(regexp = Validate.REGEX_PASSWORD, message = Validate.MESSAGE_PASSWORD)
    private String password;
}
