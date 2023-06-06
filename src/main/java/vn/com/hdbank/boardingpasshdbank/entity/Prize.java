package vn.com.hdbank.boardingpasshdbank.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prize {
    private String id;

    private String prizeCode;

    private BigDecimal prizeAmount;

    private String customerId;

    private boolean used;

    private LocalDateTime createdAt;
}