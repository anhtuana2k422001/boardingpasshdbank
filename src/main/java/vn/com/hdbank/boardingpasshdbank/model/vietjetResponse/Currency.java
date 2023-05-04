package vn.com.hdbank.boardingpasshdbank.model.vietjetResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency {
    private String code;
    private String description;
    private boolean baseCurrency;
    private double currentExchangeRate;
}
