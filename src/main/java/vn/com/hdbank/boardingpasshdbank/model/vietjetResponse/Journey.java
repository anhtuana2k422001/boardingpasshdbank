package vn.com.hdbank.boardingpasshdbank.model.vietjetResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Journey {
    private String key;
    private Departure departure;
    private ArrayList<FlightSegment> flightSegments;
}
