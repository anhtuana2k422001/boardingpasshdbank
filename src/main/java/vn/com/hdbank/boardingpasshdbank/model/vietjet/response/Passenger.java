package vn.com.hdbank.boardingpasshdbank.model.vietjet.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Passenger {
    private String lastName;
    private String firstName;
    private String birthDate;
}
