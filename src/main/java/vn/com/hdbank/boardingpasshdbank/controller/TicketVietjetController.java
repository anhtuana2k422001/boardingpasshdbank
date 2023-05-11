package vn.com.hdbank.boardingpasshdbank.controller;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.response.TicketVietjetInformation;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketRequest;
import vn.com.hdbank.boardingpasshdbank.service.impl.TicketVietJetServiceImpl;
import vn.com.hdbank.boardingpasshdbank.utils.CommonUtils;
import vn.com.hdbank.boardingpasshdbank.utils.WriteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/flight-ticket")
public class TicketVietjetController {
    private static final String CLASS_NAME = TicketVietjetController.class.getSimpleName();
    @Autowired
    TicketVietJetServiceImpl ticketVietJetService;

    //API 1
    @PostMapping("/check-flight-ticket")
    public ResponseEntity<ResponseInfo<TicketVietjetInformation>> checkInformationTicket(@Valid @RequestBody TicketRequest ticketRequest, BindingResult bindingResult) {

        String uuid = UUID.randomUUID().toString();
        final String METHOD_NAME = "checkInformationTicket";
        CommonUtils.handleValidationErrors(bindingResult); //validate

        TicketVietjetInformation ticketVietjetInformation = ticketVietJetService.checkTicketVietJet(ticketRequest);
        ResponseInfo<TicketVietjetInformation> response = ResponseInfo.<TicketVietjetInformation>builder()
                .data(ticketVietjetInformation)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();

        //LOGGER.info("ResponseInfo: {}", JsonUtils.toJsonString(response));
        // Logging here
        WriteLog.infoLog(CLASS_NAME, METHOD_NAME, ticketVietjetInformation);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
