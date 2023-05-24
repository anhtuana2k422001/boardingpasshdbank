package vn.com.hdbank.boardingpasshdbank.service.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.ApiUrls;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.ResponseService;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketScanRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.response.*;
import vn.com.hdbank.boardingpasshdbank.model.response.TicketVietJetInformation;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketRequest;
import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietJet;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietJetRepository;
import org.springframework.stereotype.Service;
import vn.com.hdbank.boardingpasshdbank.service.TicketVietJetService;
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
public class TicketVietJetServiceImpl implements TicketVietJetService {
    private final UserAuthVietJet userAuthVietJet;
    private final TicketVietJetRepository ticketVietjetRepository;

    /* Case 1: Service Self-entering information */
    @Override
    public ResponseEntity<ResponseInfo<TicketVietJetInformation>> checkTicketVietJet(TicketRequest request,
                                                                                     BindingResult bindingResult) {
        String requestId = request.getRequestId();
        String flightCode = request.getFlightCode();
        String reservationCode = request.getReservationCode();
        String seats = request.getSeats();
        /*------------------- Validate ticket request ------------------- */
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            return ResponseService.validateResponseEntity(errors, request.getRequestId());

        /*------------------- Validate Access Api -------------------*/
        ApiResponseStatus responseApi =  ApiVietJetValidation.validateApiTickKet(request,
                userAuthVietJet.getUserName(), userAuthVietJet.getPassWord());
        if (!StringUtils.equals(Constant.SUCCESS_CODE, responseApi.getStatusCode())) {
            return ResponseService.errorResponseEntity(responseApi, requestId);
        }

        /*------------------- Validate database -------------------*/
        ApiResponseStatus responseDb = DatabaseValidation.validateTicket(reservationCode, ticketVietjetRepository);
        if (!StringUtils.equals(Constant.SUCCESS_CODE, responseDb.getStatusCode())) {
            return ResponseService.errorResponseEntity(responseDb, requestId);
        }

        String jwtResponse = ApiHttpClient.getToken(ApiUrls.AUTHENTICATION_URL, userAuthVietJet.getUserName(), userAuthVietJet.getPassWord());
        String ticketResponse = ApiVietJet.callApiPassenger(jwtResponse, request);
        TicKet ticKet = JsonUtils.fromJsonString(ticketResponse, TicKet.class);

        if(ticKet != null){
            /* Handle ticKet response and save */
            TicketVietJetInformation ticketVietjetInformation = handleTicketResponse(
                    ticKet, flightCode, reservationCode, seats);
            return ResponseService.successResponseEntity(ApiResponseStatus.SUCCESS,
                    ticketVietjetInformation, requestId);
        }

        return ResponseService.errorResponseEntity(ApiResponseStatus.RESPONSE_API_ERROR, requestId);
    }

    /* Case 2: Service Scan Boarding pass */
    @Override
    public ResponseEntity<ResponseInfo<TicketVietJetInformation>> checkScanTicketVietJet(TicketScanRequest request,
                                                                                         BindingResult bindingResult) {
        String requestId = request.getRequestId();
        String flightCode = request.getFlightCode();
        String reservationCode = request.getReservationCode();
        String seats = request.getSeats();

        /*------------------- Validate ticket request -------------------*/
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            return ResponseService.validateResponseEntity(errors, request.getRequestId());

        /*------------------- Validate Access Api -------------------*/
        ApiResponseStatus responseApi = ApiVietJetValidation.validateApiTickKetScan(request,
                userAuthVietJet.getUserName(), userAuthVietJet.getPassWord());
        if (!StringUtils.equals(Constant.SUCCESS_CODE, responseApi.getStatusCode())) {
            return ResponseService.errorResponseEntity(responseApi, requestId);
        }

        /*-------------------  Validate database -------------------*/
        ApiResponseStatus responseDb = DatabaseValidation.validateTicket(reservationCode, ticketVietjetRepository);
        if (!StringUtils.equals(Constant.SUCCESS_CODE, responseDb.getStatusCode())) {
            return ResponseService.errorResponseEntity(responseDb, requestId);
        }

        String jwtResponse = ApiHttpClient.getToken(ApiUrls.AUTHENTICATION_URL,
                userAuthVietJet.getUserName(), userAuthVietJet.getPassWord());
        String ticketResponse = ApiVietJet.callApiScanPassenger(jwtResponse, request);
        TicKet ticKet = JsonUtils.fromJsonString(ticketResponse, TicKet.class);

        if(ticKet != null){
           /* Handle ticKet response and save */
            TicketVietJetInformation ticketVietjetInformation = handleTicketResponse(
                    ticKet, flightCode, reservationCode, seats);
           return ResponseService.successResponseEntity(ApiResponseStatus.SUCCESS,
                   ticketVietjetInformation, requestId);
        }

        return ResponseService.errorResponseEntity(ApiResponseStatus.RESPONSE_API_ERROR, requestId);
    }

    /* Return ticket information and save database */
    private TicketVietJetInformation handleTicketResponse(TicKet ticKet, String flightCode,
                                                      String reservationCode, String seats){
        Passenger firstPassenger = ticKet.getPassengers().get(0);
        Journey firstJourney = ticKet.getJourneys().get(0);
        List<Charge> charges = ticKet.getCharges();

        Double totalAmount = charges.stream()
                                    .filter(c -> Constant.AMOUNT_FA.equals(c.getChargeCode()))
                                    .mapToDouble(c -> c.getCurrencyAmounts().get(0).getTotalAmount())
                                    .sum();

        /* Save database */
        if (!ticketVietjetRepository.checkExistsByFlightCode(reservationCode)) {
            TicketVietJet saveTicket = new TicketVietJet(firstPassenger.getFirstName(),
                    firstPassenger.getLastName(), flightCode, reservationCode, seats);
            ticketVietjetRepository.create(saveTicket);
        }

        return new TicketVietJetInformation(
                StringUtils.join(firstPassenger.getLastName(), StringUtils.SPACE, firstPassenger.getFirstName()),
                firstPassenger.getBirthDate(),
                String.valueOf(firstJourney.getFlightSegments().get(0).getScheduledDepartureLocalDatetime()),
                totalAmount
        );
    }
}