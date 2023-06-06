package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;

import jakarta.validation.constraints.*;
import lombok.*;
import vn.com.hdbank.boardingpasshdbank.common.Validate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InfoPrizeRequest extends BaseRequest{
    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @NotEmpty (message = Validate.MESSAGE_NOT_EMPTY)
    private String customerId;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @Pattern(regexp = Validate.REGEX_PRIZE_CODE, message = Validate.MESSAGE_PRIZE_CODE)
    private String prizeCode;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @DecimalMin(value = "0", inclusive = false, message = Validate.MESSAGE_TOTAL_AMOUNT)
    private Double totalAmount;
}
