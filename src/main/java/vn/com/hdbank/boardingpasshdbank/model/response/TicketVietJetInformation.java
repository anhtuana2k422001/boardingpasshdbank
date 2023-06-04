package vn.com.hdbank.boardingpasshdbank.model.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketVietJetInformation {
    private String ticketId;
    private String fullName;
    private String birthDate;
    private String flightTime;
    private Double totalAmount;
}
