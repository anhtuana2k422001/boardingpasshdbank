package com.hoanhtuan.boardingpasshdbank.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerRequest {
    private String fullName;
    private String flightCode;
    private String reservationCode;
    private String seats;
}
