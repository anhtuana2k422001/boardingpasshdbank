package vn.com.hdbank.boardingpasshdbank.model.vietjetResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerLegDetail {
    private String passengerkey;
    private String journeykey;
    private String segmentkey;
    private double totalAmount;
    private String currencyCode;
    private TravelStatus travelStatus;
    private ReservationStatus reservationStatus;
}
