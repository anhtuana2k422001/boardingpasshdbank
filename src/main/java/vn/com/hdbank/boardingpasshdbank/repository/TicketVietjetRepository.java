package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietjet;

import java.util.List;

public interface TicketVietjetRepository {
    void create(TicketVietjet ticketVietjet);
    List<TicketVietjet> getAllTicketVietjets();
    void updateTicketVietjet(TicketVietjet ticketVietjet);
    void deleteTicketVietjet(int id);
    List<TicketVietjet> findByFlightCodeAndPassengerIdIsNotNull(String flightCode);
    boolean checkExistsByFlightCode(String flightCode);
}
