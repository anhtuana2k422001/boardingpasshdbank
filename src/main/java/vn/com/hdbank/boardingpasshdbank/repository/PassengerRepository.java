package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.model.vietjet.PassengerInformation;

import java.util.List;

public interface PassengerRepository {
    List<PassengerInformation> findPassengerByFlightCodeUsed(String flightCode);
    void create(PassengerInformation passengerInformation);
}
