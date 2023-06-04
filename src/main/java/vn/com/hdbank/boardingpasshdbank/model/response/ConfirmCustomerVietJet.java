package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.*;
import vn.com.hdbank.boardingpasshdbank.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.entity.Prize;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfirmCustomerVietJet {
    private Customer customer;
    private Prize prizes;
    private String linkWebViewPrizes;
}
