package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrizeResult {
    private String statusInformation;
    private String bankAccount;
    private String balanceAfterTransaction;
    private  double totalAmount;
}
