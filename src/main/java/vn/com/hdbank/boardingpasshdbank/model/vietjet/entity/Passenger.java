package vn.com.hdbank.boardingpasshdbank.model.vietjet.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Passenger {
    private int id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String customerType;
    private LocalDateTime createAt;
}
