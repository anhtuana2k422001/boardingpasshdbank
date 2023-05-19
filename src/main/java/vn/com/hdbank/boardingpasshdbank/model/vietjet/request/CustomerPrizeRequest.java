package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;

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
    private Integer customerId;
}
