package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.*;
import vn.com.hdbank.boardingpasshdbank.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.entity.Prize;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfirmCustomerVietJet {
    private Customer customer;
    private List<Prize> prizes;
    private String linkWebViewPrizes;
}
