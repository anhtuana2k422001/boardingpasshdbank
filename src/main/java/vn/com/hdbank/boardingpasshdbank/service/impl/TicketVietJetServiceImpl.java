package vn.com.hdbank.boardingpasshdbank.service.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.ApiUrls;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.ResponseEntityHelper;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketScanRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.response.*;
import vn.com.hdbank.boardingpasshdbank.model.response.TicketVietJetInformation;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketRequest;
import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietJet;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietJetRepository;
import org.springframework.stereotype.Service;
import vn.com.hdbank.boardingpasshdbank.service.utils.ApiVietJet;
import vn.com.hdbank.boardingpasshdbank.service.validate.ApiVietJetValidation;
import vn.com.hdbank.boardingpasshdbank.service.validate.DatabaseValidation;
import vn.com.hdbank.boardingpasshdbank.utils.ApiHttpClient;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;
import vn.com.hdbank.boardingpasshdbank.utils.ValidationUtils;


import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TicketVietJetServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketVietJetServiceImpl.class);
    private final UserAuthVietJet userAuthVietJet;
    private final TicketVietJetRepository ticketVietjetRepository;

    /* Case 1: Service Self-entering information */
    public ResponseEntity<ResponseInfo<TicketVietJetInformation>> checkTicketVietJet(TicketRequest request,
                                                                                     BindingResult bindingResult) {
        String requestId = request.getRequestId();
        String flightCode = request.getFlightCode();
        String reservationCode = request.getReservationCode();
        String seats = request.getSeats();
        /*Validate ticket request*/
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            return ResponseEntityHelper.validateResponseEntity(errors, request.getRequestId());
        /*------------------- Validate Access Api -------------------*/
        ApiResponseStatus responseApi =  ApiVietJetValidation.validateApiTickKet(request,
                userAuthVietJet.getUserName(), userAuthVietJet.getPassWord());
        if (!StringUtils.equals(Constant.SUCCESS_CODE, responseApi.getStatusCode())) {
            return ResponseEntityHelper.errorResponseEntity(responseApi, requestId);
        }
        /*------------------- Validate database -------------------*/
        ApiResponseStatus responseDb = DatabaseValidation.validateTicket(reservationCode, ticketVietjetRepository);
        if (!StringUtils.equals(Constant.SUCCESS_CODE, responseDb.getStatusCode())) {
            return ResponseEntityHelper.errorResponseEntity(responseDb, requestId);
        }
        String jwtResponse = ApiHttpClient.getToken(ApiUrls.AUTHENTICATION_URL, userAuthVietJet.getUserName(), userAuthVietJet.getPassWord());
        String ticketResponse = ApiVietJet.callApiPassenger(jwtResponse, request);
        /* Handle response and save */
        TicketVietJetInformation ticketVietjetInformation = handleTicketResponse(
                ticketResponse, flightCode, reservationCode, seats
        );
        return ResponseEntityHelper.successResponseEntity(ApiResponseStatus.SUCCESS,
                ticketVietjetInformation, requestId);
    }

    /* Case 2: Service Scan Boarding pass */
    public ResponseEntity<ResponseInfo<TicketVietJetInformation>> checkScanTicketVietJet(TicketScanRequest request,
                                                                                         BindingResult bindingResult) {
        String requestId = request.getRequestId();
        String flightCode = request.getFlightCode();
        String reservationCode = request.getReservationCode();
        String seats = request.getSeats();
        /*------------------- Validate ticket request -------------------*/
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            return ResponseEntityHelper.validateResponseEntity(errors, request.getRequestId());
        /*------------------- Validate Access Api -------------------*/
        ApiResponseStatus responseApi = ApiVietJetValidation.validateApiTickKetScan(request,
                userAuthVietJet.getUserName(), userAuthVietJet.getPassWord());
        if (!StringUtils.equals(Constant.SUCCESS_CODE, responseApi.getStatusCode())) {
            return ResponseEntityHelper.errorResponseEntity(responseApi, requestId);
        }
        /*-------------------  Validate database -------------------*/
        ApiResponseStatus responseDb = DatabaseValidation.validateTicket(reservationCode, ticketVietjetRepository);
        if (!StringUtils.equals(Constant.SUCCESS_CODE, responseDb.getStatusCode())) {
            return ResponseEntityHelper.errorResponseEntity(responseDb, requestId);
        }
        String jwtResponse = ApiHttpClient.getToken(ApiUrls.AUTHENTICATION_URL, userAuthVietJet.getUserName(), userAuthVietJet.getPassWord());
        String ticketResponse = ApiVietJet.callApiScanPassenger(jwtResponse, request);
        /* Handle response and save */
        TicketVietJetInformation ticketVietjetInformation = handleTicketResponse(
                ticketResponse, flightCode, reservationCode, seats
        );
        return ResponseEntityHelper.successResponseEntity(ApiResponseStatus.SUCCESS,
                ticketVietjetInformation, requestId);
    }

    /* Return ticket information and save database */
    private TicketVietJetInformation handleTicketResponse(String ticketResponse, String flightCode,
                                                      String reservationCode, String seats){
        TicKet ticKet = JsonUtils.fromJsonString(ticketResponse, TicKet.class);
        Passenger firstPassenger = ticKet.getPassengers().get(0);
        Journey firstJourney = ticKet.getJourneys().get(0);
        List<Charge> charges = ticKet.getCharges();
        Double totalAmount = charges.stream().filter(c -> "FA"
                .equals(c.getChargeCode())).mapToDouble(c -> c.getCurrencyAmounts()
                .get(0).getTotalAmount()).sum();
        /* Save database */
        if (!ticketVietjetRepository.checkExistsByFlightCode(reservationCode)) {
            TicketVietJet saveTicket = new TicketVietJet(firstPassenger.getFirstName(), firstPassenger.getLastName(), flightCode, reservationCode, seats);
            ticketVietjetRepository.create(saveTicket);
        }
        return new TicketVietJetInformation(
                String.format("%s %s", firstPassenger.getLastName(), firstPassenger.getFirstName()),
                firstPassenger.getBirthDate(),
                String.valueOf(firstJourney.getFlightSegments().get(0).getScheduledDepartureLocalDatetime()),
                totalAmount
        );
    }

}