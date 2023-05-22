package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietJet;

import java.util.List;

public interface TicketVietJetRepository {
    void create(TicketVietJet ticketVietjet);
    List<TicketVietJet> findCustomerIdNotNull(String flightCode);
    boolean checkExistsByFlightCode(String flightCode);
    void updateCustomerIdByFlightCode(int customerId,String flightCode);
}
