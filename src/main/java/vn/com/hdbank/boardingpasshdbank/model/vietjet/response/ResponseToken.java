package vn.com.hdbank.boardingpasshdbank.model.vietjet.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseToken {
    private String token;
    private String expiration;
}
