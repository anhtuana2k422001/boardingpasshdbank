package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.*;
import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerPrizeStatus {
    private Customer customer;
    private String statusPrize;
    private BigDecimal totalAmount;
    private String linkWebViewPrizes;
}
