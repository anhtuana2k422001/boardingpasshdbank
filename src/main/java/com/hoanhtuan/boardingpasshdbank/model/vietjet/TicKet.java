package com.hoanhtuan.boardingpasshdbank.model.vietjet;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class TicKet {
    private String locator;
    private ArrayList<Passenger> passengers;
    private ArrayList<Journey> journeys;
    private ArrayList<Charge> charges;
    private ArrayList<SeatSelection> seatSelections;
    private ArrayList<PassengerLegDetail> passengerLegDetails;
}
