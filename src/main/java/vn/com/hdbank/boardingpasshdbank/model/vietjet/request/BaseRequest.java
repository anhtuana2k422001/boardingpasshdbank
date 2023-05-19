package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import vn.com.hdbank.boardingpasshdbank.common.Validate;

@Setter
@Getter
public class BaseRequest {
    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @NotEmpty (message = Validate.MESSAGE_NOT_EMPTY)
    private String requestId;
}
