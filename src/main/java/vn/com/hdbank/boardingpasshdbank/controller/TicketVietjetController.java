package vn.com.hdbank.boardingpasshdbank.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.response.TicketVietjetInformation;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketRequest;
import vn.com.hdbank.boardingpasshdbank.service.impl.TicketVietJetServiceImpl;
import vn.com.hdbank.boardingpasshdbank.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api")
public class TicketVietjetController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketVietjetController.class);
    @Autowired
    TicketVietJetServiceImpl ticketVietJetService;
    @PostMapping("/check-flight-ticket")
    public ResponseEntity<ResponseInfo> checkInformationTicket(@Valid @RequestBody TicketRequest reservationRequestDTO, BindingResult bindingResult) {
        String requestId = UUID.randomUUID().toString();
        CommonUtils.handleValidationErrors(bindingResult); /*validate*/
        MDC.put("requestId", requestId);
        TicketVietjetInformation ticketVietjetInformation = ticketVietJetService.checkPassengerVietJet(reservationRequestDTO);
        ResponseInfo response = ResponseInfo.builder()
                .data(ticketVietjetInformation)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        LOGGER.info("Response: {}", JsonUtils.toJsonString(response));
        MDC.clear();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
