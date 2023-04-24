package com.hoanhtuan.boardingpasshdbank.controller;

import com.hoanhtuan.boardingpasshdbank.model.CustomerTicketInformation;
import com.hoanhtuan.boardingpasshdbank.model.response.ResponseStatus;
import com.hoanhtuan.boardingpasshdbank.service.impl.TicketVietJetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path="api")
public class PassengerController {
    @Autowired
    TicketVietJetServiceImpl ticketVietJetService;

    // Case 1:self enter information: 3 param input
    @GetMapping("/passenger")
    public ResponseEntity<ResponseStatus> checkInformationTicket(@RequestParam(required = false) String fullName,
                                                                 @RequestParam String flightCode,
                                                                 @RequestParam String reservationCode,
                                                                 @RequestParam String seats) throws IOException {
        CustomerTicketInformation customerTicketInformation = ticketVietJetService.checkPassengerVietJet(fullName, flightCode, reservationCode, seats);
        ResponseEntity<ResponseStatus> responseEntity;
        ResponseStatus response = ResponseStatus.builder()
                .data(customerTicketInformation)
                .responseMessage("Valid Ticket Information")
                .responseCode(200)
                .build();
        // logging here
        responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        return  responseEntity;
    }
}
