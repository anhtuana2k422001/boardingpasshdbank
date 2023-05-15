package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPrizeRequest extends BaseRequest{
    private String prizeCode;
    private Double totalAmount;
}
