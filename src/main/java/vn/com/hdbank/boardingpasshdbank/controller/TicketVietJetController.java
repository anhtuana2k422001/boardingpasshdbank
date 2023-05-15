package vn.com.hdbank.boardingpasshdbank.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.ResponseEntityHelper;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.response.TicketVietjetInformation;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketScanRequest;
import vn.com.hdbank.boardingpasshdbank.service.impl.TicketVietJetServiceImpl;
import vn.com.hdbank.boardingpasshdbank.utils.MdcUtils;
import vn.com.hdbank.boardingpasshdbank.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;


@RestController
@RequestMapping(path = "/api/flight-ticket")
public class TicketVietJetController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketVietJetController.class);
    @Autowired
    TicketVietJetServiceImpl ticketVietJetService;
    /* API 1: check-flight-ticketVietJet */
    // Case 1: Self-entering information
    @PostMapping("/check-flight-ticket")
    public ResponseEntity<ResponseInfo<TicketVietjetInformation>> checkInformationTicket(@Valid @RequestBody TicketRequest request,
                                                                                         BindingResult bindingResult) {
        ValidationUtils.handleValidationErrors(bindingResult); /* Validate */
        MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
        String requestLog = JsonUtils.toJsonString(request); /* Log request */
        LOGGER.info(Constant.REQUEST, requestLog);
        TicketVietjetInformation ticketVietjetInformation = ticketVietJetService.checkTicketVietJet(request);
        ResponseEntity<ResponseInfo<TicketVietjetInformation>> responseEntity;
        if (ticketVietjetInformation != null){
            responseEntity = ResponseEntityHelper.createSuccessResponseEntity(ticketVietjetInformation);
        }else{
            responseEntity = ResponseEntityHelper.createErrorResponseEntity(ApiResponseStatus.CHECK_TICKET_ERROR);
        }
        String response = JsonUtils.toJsonString(responseEntity);
        LOGGER.info(Constant.RESPONSE, response);   /* Log response */
        MDC.clear();
        return responseEntity;
    }

    // Case 2: Scan Boarding pass
    @PostMapping("/check-flight-ticket-scan")
    public ResponseEntity<ResponseInfo<TicketVietjetInformation>> checkInformationTicketScan(@Valid @RequestBody TicketScanRequest request,
                                                                                         BindingResult bindingResult) {
        ValidationUtils.handleValidationErrors(bindingResult); /* Validate */
        MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
        String requestLog = JsonUtils.toJsonString(request); /* Log request */
        LOGGER.info(Constant.REQUEST, requestLog);
        return null;
    }

}
