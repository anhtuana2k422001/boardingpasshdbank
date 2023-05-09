package vn.com.hdbank.boardingpasshdbank.model.vietjetResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyAmount {
    private double baseAmount;
    private double discountAmount;
    private double taxAmount;
    private double totalAmount;
    private double exchangeRate;
}
