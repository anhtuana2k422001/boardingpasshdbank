package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseInfo {
    private int statusCode;
    private String statusMessage;
    private Object data;
}
