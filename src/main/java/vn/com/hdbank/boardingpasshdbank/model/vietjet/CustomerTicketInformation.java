package vn.com.hdbank.boardingpasshdbank.model.vietjet;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerTicketInformation {
    private String fullName;
    private String birthDate;
    private String flightTime;
    private Double totalAmount;
}
