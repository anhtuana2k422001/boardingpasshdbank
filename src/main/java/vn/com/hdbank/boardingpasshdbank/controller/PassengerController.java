package vn.com.hdbank.boardingpasshdbank.controller;

import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.CustomerTicketInformation;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.ReservationRequestDTO;
import vn.com.hdbank.boardingpasshdbank.service.impl.TicketVietJetServiceImpl;
import vn.com.hdbank.boardingpasshdbank.utils.WriteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class PassengerController {
    // Get class Name
    private static final String CLASS_NAME  = PassengerController.class.getName();
    @Autowired
    TicketVietJetServiceImpl ticketVietJetService;

    @PostMapping("/passengerVietjet")
    public ResponseEntity<ResponseInfo> checkInformationTicket(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        final String METHOD_NAME = "checkInformationTicket";
        try {
            CustomerTicketInformation customerTicketInformation = ticketVietJetService.checkPassengerVietJet(reservationRequestDTO);
            ResponseInfo response = ResponseInfo.builder()
                    .data(customerTicketInformation)
                    .apiResponseStatus(ApiResponseStatus.SUCCESS)
                    .build();
            // Logging here
            WriteLog.infoLog(CLASS_NAME, METHOD_NAME, customerTicketInformation);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
