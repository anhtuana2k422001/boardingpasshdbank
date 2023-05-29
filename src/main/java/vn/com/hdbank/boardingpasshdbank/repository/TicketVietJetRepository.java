package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.entity.TicketVietJet;

import java.util.List;

public interface TicketVietJetRepository {
    void create(TicketVietJet ticketVietjet);
    List<TicketVietJet> findCustomerIdNotNull(String reservationCode);
    boolean checkExistsByFlightCode(String reservationCode);
    void updateCustomerIdByFlightCode(int customerId,String reservationCode);
}
