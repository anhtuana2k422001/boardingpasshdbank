package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.entity.Customer;

import java.util.Date;

public interface CustomerRepository {
    void updateCustomerVJ(String customerType, int customerId);
    Customer findById(int customerId);
    Customer findByPhoneNumber(String phoneNumber);
    boolean checkInfoCustomer(String ticketId, String fistNameCustomer, String lastNameCustomer, Date birthDateCustomer);
    boolean checkInfoCustomer(String ticketId, int customerId);
    boolean checkTicketExist(String ticketId);
    boolean checkCustomerUsedTicket(int customerId);
}
