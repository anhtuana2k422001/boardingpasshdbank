package vn.com.hdbank.boardingpasshdbank.model.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketVietjet {
    private int id;
    private String firstName;
    private String lastName;
    private String flightCode;
    private String reservationCode;
    private String seats;
    private int customerId;
    private LocalDateTime createAt;

    public TicketVietjet(String firstName, String lastName, String flightCode, String reservationCode, String seats) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.flightCode = flightCode;
        this.reservationCode = reservationCode;
        this.seats = seats;
    }
}
