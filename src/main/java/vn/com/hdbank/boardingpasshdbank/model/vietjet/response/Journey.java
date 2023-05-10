package vn.com.hdbank.boardingpasshdbank.model.vietjet.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Journey {
    private ArrayList<FlightSegment> flightSegments;
}
