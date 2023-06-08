package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfirmCustomerVietJet {
    private CustomerInfo customer;
    private PrizeInfo prizes;
    private String linkWebViewPrizes;
}
