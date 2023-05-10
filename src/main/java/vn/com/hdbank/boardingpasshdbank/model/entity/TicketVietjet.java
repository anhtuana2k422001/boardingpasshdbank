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
    private String lastName;
    private String firstName;
    private String flightCode;
    private String reservationCode;
    private String seats;
    private int passengerId;
    private LocalDateTime createAt;
}
