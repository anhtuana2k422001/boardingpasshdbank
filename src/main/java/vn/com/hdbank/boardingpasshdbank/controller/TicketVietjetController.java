package vn.com.hdbank.boardingpasshdbank.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
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
import vn.com.hdbank.boardingpasshdbank.utils.Utils;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/flight-ticket")
public class TicketVietjetController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketVietjetController.class);
    @Autowired
    TicketVietJetServiceImpl ticketVietJetService;

    /* API 1: check-flight-ticketVietJet */
    @PostMapping("/check-flight-ticket")
    public ResponseEntity<ResponseInfo<TicketVietjetInformation>> checkInformationTicket(@Valid @RequestBody TicketRequest request,
                                                                                         BindingResult bindingResult) {
        CommonUtils.handleValidationErrors(bindingResult); /*validate*/
        if(Utils.isEmpty(request.getRequestId())){
            String requestId = UUID.randomUUID().toString();
            MDC.put("requestId", requestId);
        }
        MDC.put("requestId", request.getRequestId());
        String requestLog = JsonUtils.toJsonString(request);
        LOGGER.info(Constant.REQUEST, requestLog);
        TicketVietjetInformation ticketVietjetInformation = ticketVietJetService.checkTicketVietJet(request);
        ResponseInfo<TicketVietjetInformation> response = ResponseInfo.<TicketVietjetInformation>builder()
                .data(ticketVietjetInformation)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        String responseLog = JsonUtils.toJsonString(response);
        LOGGER.info(Constant.RESPONSE, responseLog);
        MDC.clear();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
