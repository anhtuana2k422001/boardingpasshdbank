package vn.com.hdbank.boardingpasshdbank.model.vietjet.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPrizeRequest extends BaseRequest {
    private int customerId;
}
