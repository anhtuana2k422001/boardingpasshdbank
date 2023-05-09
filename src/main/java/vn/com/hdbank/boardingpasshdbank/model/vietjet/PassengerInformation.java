package vn.com.hdbank.boardingpasshdbank.model.vietjet;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PassengerInformation {
    private int id;
    private String fullName;
    private String birthDate;
    private String customerType;
    private Timestamp createAt;
    // chia bang Ticket moi
    private String flightCode;
    private String flightTime;
    private String reservationCode;
    private String seats;
    private double totalAmountTicket;
    private boolean isUsed;
}
