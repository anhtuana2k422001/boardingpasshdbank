package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.model.entity.Passenger;

import java.util.List;

public interface PassengerRepository {
    void create(Passenger passenger);
    List<Passenger> getAll();
    void update(Passenger passenger);
    void delete(int passengerId);
}
