package com.hoanhtuan.boardingpasshdbank.model.vietjet;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Charge {
    private String chargeTime;
    private String chargeCode;
    private ArrayList<CurrencyAmount> currencyAmounts;
}
