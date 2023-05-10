package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketVietjetInformation {
    private String fullName;
    private String birthDate;
    private String flightTime;
    private Double totalAmount;
}
