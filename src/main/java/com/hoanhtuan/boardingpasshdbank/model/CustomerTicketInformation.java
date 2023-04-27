package com.hoanhtuan.boardingpasshdbank.model;

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
