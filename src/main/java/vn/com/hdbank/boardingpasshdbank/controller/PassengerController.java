package vn.com.hdbank.boardingpasshdbank.controller;

import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.PassengerVietjetInformationDTO;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.ReservationRequestDTO;
import vn.com.hdbank.boardingpasshdbank.service.impl.TicketVietJetServiceImpl;
import vn.com.hdbank.boardingpasshdbank.utils.CommonUtils;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;
import vn.com.hdbank.boardingpasshdbank.utils.WriteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class PassengerController {
    private static final String CLASS_NAME = PassengerController.class.getSimpleName();
    @Autowired
    TicketVietJetServiceImpl ticketVietJetService;

    @PostMapping("/check-flight-ticket")
    public ResponseEntity<ResponseInfo> checkInformationTicket(@Valid @RequestBody ReservationRequestDTO reservationRequestDTO, BindingResult bindingResult) {

        String uuid = UUID.randomUUID().toString();
        final String METHOD_NAME = "checkInformationTicket";
        CommonUtils.handleValidationErrors(bindingResult); //validate

        PassengerVietjetInformationDTO passengerVietjetInformationDTO = ticketVietJetService.checkPassengerVietJet(reservationRequestDTO);
        ResponseInfo response = ResponseInfo.builder()
                .data(passengerVietjetInformationDTO)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();

        //LOGGER.info("ResponseInfo: {}", JsonUtils.toJsonString(response));
        // Logging here
        WriteLog.infoLog(CLASS_NAME, METHOD_NAME, passengerVietjetInformationDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
