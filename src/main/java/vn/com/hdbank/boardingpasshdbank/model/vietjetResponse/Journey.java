package vn.com.hdbank.boardingpasshdbank.model.vietjetResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Journey {
    private String key;
    private Departure departure;
    private ArrayList<FlightSegment> flightSegments;
}
