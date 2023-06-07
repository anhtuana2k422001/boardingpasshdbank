package vn.com.hdbank.boardingpasshdbank.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfo {
    private String id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String address;

    private String customerType;

}
