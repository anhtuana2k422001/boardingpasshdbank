package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultPrize {
    private String message;
    private Double totalAmount;
}
