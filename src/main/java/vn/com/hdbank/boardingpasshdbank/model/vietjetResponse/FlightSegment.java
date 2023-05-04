package vn.com.hdbank.boardingpasshdbank.model.vietjetResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightSegment {
    private String key;
    private Origin origin;
    private Destination destination;
    private Carrier carrier;
    private String scheduledDepartureLocalDatetime;
    private String scheduledArrivalLocalDatetime;
}
