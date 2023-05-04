package vn.com.hdbank.boardingpasshdbank.model.vietjetResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CurrencyAmount {
    private double baseAmount;
    private double discountAmount;
    private double taxAmount;
    private ArrayList<TaxRateAmount> taxRateAmounts;
    private double totalAmount;
    private Currency currency;
    private double exchangeRate;
}
