package vn.com.hdbank.boardingpasshdbank.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketVietJet {
    private String id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Timestamp flightTime;
    private String flightCode;
    private String reservationCode;
    private String seats;
    private Double totalAmount;
    private String customerId;
    private LocalDateTime createAt;
}
