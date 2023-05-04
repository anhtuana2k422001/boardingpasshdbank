package com.hoanhtuan.boardingpasshdbank.controller;

import com.hoanhtuan.boardingpasshdbank.common.Constant;
import com.hoanhtuan.boardingpasshdbank.common.ResultCode;
import com.hoanhtuan.boardingpasshdbank.model.CustomerTicketInformation;
import com.hoanhtuan.boardingpasshdbank.model.request.PassengerRequest;
import com.hoanhtuan.boardingpasshdbank.model.response.ResponseStatus;
import com.hoanhtuan.boardingpasshdbank.service.impl.TicketVietJetServiceImpl;
import com.hoanhtuan.boardingpasshdbank.utils.WriteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.hoanhtuan.boardingpasshdbank.common.ResultCode.RESULT_CODE_SUCCESS;

@RestController
@RequestMapping(path = "api/v1")
public class PassengerController {
    private static final String CLASS_NAME  = PassengerController.class.getName();
    @Autowired
    TicketVietJetServiceImpl ticketVietJetService;

    // Case 1:self enter information with 3 input
    // Case 2:scan with 5 input
    @PostMapping("/passenger-vietjet")
    public ResponseEntity<ResponseStatus> checkInformationTicket(@RequestBody  PassengerRequest  request) throws IOException {
        String methodName = "checkInformationTicket";
        CustomerTicketInformation customerTicketInformation = ticketVietJetService.checkPassengerVietJet(
                request.getFullName(), request.getFlightCode(),
                request.getReservationCode(), request.getSeats()
        );
        ResponseStatus response = ResponseStatus.builder()
                .data(customerTicketInformation)
                .responseMessage(RESULT_CODE_SUCCESS.getStatusMessage())
                .responseCode(RESULT_CODE_SUCCESS.getStatusCode())
                .build();
        // Logging here
        WriteLog.infoLog(CLASS_NAME, methodName, customerTicketInformation);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
