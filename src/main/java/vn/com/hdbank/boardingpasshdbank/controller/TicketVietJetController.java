package vn.com.hdbank.boardingpasshdbank.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.ResponseEntityHelper;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.response.TicketVietJetInformation;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketScanRequest;
import vn.com.hdbank.boardingpasshdbank.service.impl.TicketVietJetServiceImpl;
import vn.com.hdbank.boardingpasshdbank.utils.MdcUtils;
import vn.com.hdbank.boardingpasshdbank.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/flight-ticket")
public class TicketVietJetController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketVietJetController.class);
    @Autowired
    TicketVietJetServiceImpl ticketVietJetService;
    /* API 1: check-flight-ticketVietJet */
    /* Case 1: Self-entering information */
    @PostMapping("/check-flight-ticket")
    public ResponseEntity<ResponseInfo<TicketVietJetInformation>> checkInformationTicket(@Valid @RequestBody TicketRequest request,
                                                                                         BindingResult bindingResult) {
        ResponseEntity<ResponseInfo<TicketVietJetInformation>> responseEntity;
        MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
        String requestLog = JsonUtils.toJsonString(request); /* Log request */
        LOGGER.info(Constant.REQUEST, requestLog);
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            responseEntity =  ResponseEntityHelper.validateResponseEntity(errors, request.getRequestId());
        else
            responseEntity = ticketVietJetService.checkTicketVietJet(request);
        String response = JsonUtils.toJsonString(responseEntity);
        LOGGER.info(Constant.RESPONSE, response);   /* Log response */
        MDC.clear();
        return responseEntity;
    }

    /* Case 2: Scan Boarding pass */
    @PostMapping("/check-flight-ticket-scan")
    public ResponseEntity<ResponseInfo<TicketVietJetInformation>> checkScanInformationTicket(@Valid @RequestBody TicketScanRequest request,
                                                                                             BindingResult bindingResult) {
        ResponseEntity<ResponseInfo<TicketVietJetInformation>> responseEntity;
        MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
        String requestLog = JsonUtils.toJsonString(request); /* Log request */
        LOGGER.info(Constant.REQUEST, requestLog);
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            responseEntity =  ResponseEntityHelper.validateResponseEntity(errors, request.getRequestId());
        else
            responseEntity = ticketVietJetService.checkScanTicketVietJet(request);
        String response = JsonUtils.toJsonString(responseEntity);
        LOGGER.info(Constant.RESPONSE, response);   /* Log response */
        MDC.clear();
        return responseEntity;
    }

}
