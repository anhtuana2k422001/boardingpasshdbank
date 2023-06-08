package vn.com.hdbank.boardingpasshdbank.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrizeInfo {
    private String id;
    private String prizeCode;
    private LocalDateTime createdAt;
}
