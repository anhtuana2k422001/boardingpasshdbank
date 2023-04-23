package com.hoanhtuan.boardingpasshdbank.model.vietjet;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FlightSegment {
    private String key;
    private Origin origin;
    private Destination destination;
    private Carrier carrier;
    private Date scheduledDepartureLocalDatetime;
    private Date scheduledArrivalLocalDatetime;
}
