package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.model.vietjet.entity.Passenger;

import java.util.List;

public interface PassengerRepository {
    void create(Passenger passenger);
    List<Passenger> getAll();
    void update(Passenger passenger);
    void delete(int passengerId);
}
