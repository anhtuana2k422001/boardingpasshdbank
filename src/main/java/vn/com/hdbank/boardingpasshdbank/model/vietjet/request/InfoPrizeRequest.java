package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InfoPrizeRequest extends BaseRequest{
    private int customerId;
    private String prizeCode;
    private Double totalAmount;
}
