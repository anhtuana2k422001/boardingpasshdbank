package vn.com.hdbank.boardingpasshdbank.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import vn.com.hdbank.boardingpasshdbank.common.anotation.MyColumn;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketVietJet {
    @MyColumn( name = "id")
    private String id;

    @MyColumn( name = "last_name")
    private String lastName;

    @MyColumn( name = "first_name")
    private String firstName;

    @MyColumn( name = "birth_date")
    private LocalDate birthDate;

    @MyColumn( name = "flight_time")
    private Timestamp flightTime;

    @MyColumn( name = "flight_code")
    private String flightCode;

    @MyColumn( name = "reservation_code")
    private String reservationCode;

    @MyColumn( name = "seats")
    private String seats;

    @MyColumn( name = "total_amount")
    private Double totalAmount;

    @MyColumn( name = "customer_id")
    private String customerId;

    @MyColumn( name = "created_at")
    private LocalDateTime createAt;
}
