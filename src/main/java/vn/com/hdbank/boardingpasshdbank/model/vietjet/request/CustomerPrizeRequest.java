package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.hdbank.boardingpasshdbank.common.Validate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPrizeRequest extends BaseRequest {
    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @Min(value = 1, message = Validate.MESSAGE_REQUIRED_ID)
    private Integer customerId;
}
