package vn.com.hdbank.boardingpasshdbank.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.ResponseController;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.response.TicketVietJetInformation;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketScanRequest;
import vn.com.hdbank.boardingpasshdbank.service.TicketVietJetService;
import vn.com.hdbank.boardingpasshdbank.utils.MdcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;


@RestController
@RequestMapping(path = "/api/flight-ticket")
@Slf4j
public class TicketVietJetController {
    @Autowired
    TicketVietJetService ticketVietJetService;
    /* API 1: check-flight-ticketVietJet */
    /* Case 1: Self-entering information */
    @PostMapping("/check-flight-ticket")
    public ResponseEntity<ResponseInfo<TicketVietJetInformation>> checkInformationTicket(@Valid @RequestBody TicketRequest request,

                                                                                         BindingResult bindingResult) {
        try{
            MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
            LOGGER.info(Constant.REQUEST, JsonUtils.toJsonString(request)); /* Log request */
            var responseEntity = ticketVietJetService.checkTicketVietJet(request, bindingResult);
            String response = JsonUtils.toJsonString(responseEntity);
            LOGGER.info(Constant.RESPONSE, response);   /* Log response */
            MDC.clear();
            return responseEntity;
        }catch (CustomException e){
            LOGGER.info(Constant.FORMAT_EXCEPTION, e.getStatusCode(), e.getStatusMessage());
            return ResponseController.exceptionResponseEntity(e.getApiResponseStatus(), request.getRequestId());
        }
    }

    /* Case 2: Scan Boarding pass */
    @PostMapping("/check-flight-ticket-scan")
    public ResponseEntity<ResponseInfo<TicketVietJetInformation>> checkScanInformationTicket(@Valid @RequestBody TicketScanRequest request, BindingResult bindingResult) {
        try{
            MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
            LOGGER.info(Constant.REQUEST, JsonUtils.toJsonString(request)); /* Log request */
            var responseEntity = ticketVietJetService.checkScanTicketVietJet(request, bindingResult);
            LOGGER.info(Constant.RESPONSE, JsonUtils.toJsonString(responseEntity));   /* Log response */
            return responseEntity;
        }catch (CustomException e){
            LOGGER.info(Constant.FORMAT_EXCEPTION, e.getStatusCode(), e.getStatusMessage());
            return ResponseController.exceptionResponseEntity(e.getApiResponseStatus(), request.getRequestId());
        }
    }
}
