package vn.com.hdbank.boardingpasshdbank.model.vietjet.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public final class UserAuthVietJet {
    @Value("${auth.username}")
    public String userName;
    @Value("${auth.password}")
    public String passWord;
}
