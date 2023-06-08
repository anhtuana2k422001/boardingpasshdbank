package vn.com.hdbank.boardingpasshdbank.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.hdbank.boardingpasshdbank.common.anotation.MyColumn;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prize {
    @MyColumn ( name = "id")
    private String id;

    @MyColumn ( name = "prize_code")
    private String prizeCode;

    @MyColumn ( name = "prize_amount")
    private BigDecimal prizeAmount;

    @MyColumn ( name = "customer_id")
    private String customerId;

    @MyColumn ( name = "used")
    private Boolean used;

    @MyColumn ( name = "reference_code")
    private String referenceCode;

    @MyColumn ( name = "content")
    private String content;

    @MyColumn ( name = "prizedrawday")
    private LocalDate prizeDrawDay;

    @MyColumn ( name = "created_at")
    private LocalDateTime createdAt;
}