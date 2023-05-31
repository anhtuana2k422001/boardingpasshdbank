package vn.com.hdbank.boardingpasshdbank.security;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TokenResponse {
    private String token;
    private String expiration;
}
