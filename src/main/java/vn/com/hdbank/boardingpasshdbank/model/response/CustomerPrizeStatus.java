package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerPrizeStatus {
    private boolean usedPrize;
    private String prizeStatus;
    private PrizeResult prizeResult;
    private String linkWebViewPrizes;
}
