package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;

public interface CustomerRepository {
    void updateCustomerTypeById(String customerType,int id);
    Customer findById(int id);
}
