package vn.com.hdbank.boardingpasshdbank.model.vietjetResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatSelection {
    private String passengerkey;
    private String journeykey;
    private String segmentkey;
    private String rowIdentifier;
    private String seatIdentifier;
}
