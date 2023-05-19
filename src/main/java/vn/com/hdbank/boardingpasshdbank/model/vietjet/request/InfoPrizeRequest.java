package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InfoPrizeRequest extends BaseRequest{
    @NotNull
    private Integer customerId;
    @NotNull
    @NotEmpty
    private String prizeCode;
    @NotNull
    private Double totalAmount;
}
