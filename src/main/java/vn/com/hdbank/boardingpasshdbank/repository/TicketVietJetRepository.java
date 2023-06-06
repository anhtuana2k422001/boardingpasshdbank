package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.entity.TicketVietJet;


public interface TicketVietJetRepository {
    void createTicket(TicketVietJet ticketVietjet);
    String getTicketId(String reservationCode, String flightCode, String seats);
    boolean checkExistTicket(String reservationCode, String flightCode, String seats);
    boolean checkSaveTicket(String reservationCode, String flightCode, String seats);
    void updateConfirmCustomer(String ticketId, String customerId);
}
