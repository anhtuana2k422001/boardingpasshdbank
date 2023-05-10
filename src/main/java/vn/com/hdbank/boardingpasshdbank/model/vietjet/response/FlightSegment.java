package vn.com.hdbank.boardingpasshdbank.model.vietjet.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightSegment {
    private String scheduledDepartureLocalDatetime;
}
