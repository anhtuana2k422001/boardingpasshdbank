package com.hoanhtuan.boardingpasshdbank.controller;

import com.hoanhtuan.boardingpasshdbank.common.Constant;
import com.hoanhtuan.boardingpasshdbank.model.CustomerTicketInformation;
import com.hoanhtuan.boardingpasshdbank.model.response.ResponseStatus;
import com.hoanhtuan.boardingpasshdbank.service.impl.TicketVietJetServiceImpl;
import com.hoanhtuan.boardingpasshdbank.utils.WriteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api")
public class PassengerController {
    // Get class Name
    private static final String CLASS_NAME  = PassengerController.class.getName();
    @Autowired
    TicketVietJetServiceImpl ticketVietJetService;


    // Case 1:self enter information with 3 param input
    // Case 2:scan with 5 param input
    @GetMapping("/passengerVietjet")
    public ResponseEntity<ResponseStatus> checkInformationTicket(@RequestParam(required = false) String fullName,
                                                                 @RequestParam String flightCode,
                                                                 @RequestParam String reservationCode,
                                                                 @RequestParam String seats) throws IOException {
        final String METHOD_NAME = "checkInformationTicket";
        CustomerTicketInformation customerTicketInformation = ticketVietJetService.checkPassengerVietJet(fullName, flightCode, reservationCode, seats);
        ResponseStatus response = ResponseStatus.builder()
                .data(customerTicketInformation)
                .responseMessage("Valid Ticket Information")
                .responseCode(Constant.OK)
                .build();
        // Logging here
        WriteLog.infoLog(CLASS_NAME, METHOD_NAME, customerTicketInformation);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
