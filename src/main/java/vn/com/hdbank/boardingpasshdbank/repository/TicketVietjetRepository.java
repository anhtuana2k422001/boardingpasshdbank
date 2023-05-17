package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietjet;

import java.util.List;

public interface TicketVietjetRepository {
    void create(TicketVietjet ticketVietjet);
    List<TicketVietjet> findCustomerIdNotNull(String flightCode);
    boolean checkExistsByFlightCode(String flightCode);
    void updateCustomerIdByFlightCode(int customerId,String flightCode);
}
