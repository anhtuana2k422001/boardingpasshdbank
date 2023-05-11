package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseError {
    private String code;
    private String message;
}
