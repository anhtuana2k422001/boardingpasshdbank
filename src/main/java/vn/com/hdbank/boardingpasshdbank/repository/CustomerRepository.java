package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;

import java.util.List;

public interface CustomerRepository {
    void create(Customer passenger);
    List<Customer> getAll();
    void update(Customer passenger);
    void delete(int passengerId);
}
