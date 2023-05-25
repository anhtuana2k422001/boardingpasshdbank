package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerPrizeStatus {
    private PrizeResult prizeResult;
    private String linkWebViewPrizes;
}
