package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrizeResult {
    private String statusInformation;
    private String bankAccount;
    private double balanceAfterTransaction;
    private BigDecimal totalAmount;
}
