package vn.com.hdbank.boardingpasshdbank.entity;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String customerType;
    private LocalDateTime createdAt;
}
