package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import vn.com.hdbank.boardingpasshdbank.common.Validate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InfoPrizeRequest extends BaseRequest{
    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    private Integer customerId;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    @NotEmpty (message = Validate.MESSAGE_NOT_EMPTY)
    private String prizeCode;

    @NotNull (message = Validate.MESSAGE_NOT_NULL)
    private Double totalAmount;
}
