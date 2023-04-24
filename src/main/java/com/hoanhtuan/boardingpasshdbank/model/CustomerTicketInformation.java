package com.hoanhtuan.boardingpasshdbank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTicketInformation {
    private String fullName;
    private String birthDate;
    private String flightTime;
    private Double totalAmount;
}
