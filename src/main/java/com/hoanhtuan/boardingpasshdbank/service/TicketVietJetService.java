package com.hoanhtuan.boardingpasshdbank.service;

import com.hoanhtuan.boardingpasshdbank.model.CustomerTicketInformation;

import java.io.IOException;

public interface TicketVietJetService {
    CustomerTicketInformation checkPassengerVietJet(String fullName, String flightCode, String reservationCode, String seats) throws IOException;
}
