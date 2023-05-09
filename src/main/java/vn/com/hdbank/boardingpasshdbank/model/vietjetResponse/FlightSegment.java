package vn.com.hdbank.boardingpasshdbank.model.vietjetResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightSegment {
    private String key;
    private String scheduledDepartureLocalDatetime;
    private String scheduledArrivalLocalDatetime;
}
