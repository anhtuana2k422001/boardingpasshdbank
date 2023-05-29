package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketConfirmRequest;

public interface CustomerRepository {
    void updateCustomerTypeById(String customerType,int id);
    Customer findById(int id);
    boolean usedTicket(TicketConfirmRequest request);
}
