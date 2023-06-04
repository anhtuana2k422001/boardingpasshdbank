package vn.com.hdbank.boardingpasshdbank.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import vn.com.hdbank.boardingpasshdbank.entity.TicketVietJet;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietJetRepository;
import org.springframework.stereotype.Service;
import vn.com.hdbank.boardingpasshdbank.service.TicketVietJetService;
import vn.com.hdbank.boardingpasshdbank.service.utils.ApiVietJet;
import vn.com.hdbank.boardingpasshdbank.service.validate.DatabaseValidation;
import vn.com.hdbank.boardingpasshdbank.utils.ApiHttpClient;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;
import vn.com.hdbank.boardingpasshdbank.utils.DateUtils;
import vn.com.hdbank.boardingpasshdbank.utils.ValidationUtils;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class TicketVietJetServiceImpl implements TicketVietJetService {
    private final UserAuthVietJet userAuthVietJet;
    private final TicketVietJetRepository ticketVietjetRepository;

    /* Case 1: Service Self-entering information */
    @Override
    public ResponseInfo<TicketVietJetInformation> checkTicketVietJet(TicketRequest request,
                                                                     BindingResult bindingResult) {
        String requestId = request.getRequestId();
        String flightCode = request.getFlightCode();
        String reservationCode = request.getReservationCode();
        String seats = request.getSeats();

        /*------------------- Validate ticket request ------------------- */
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            return ResponseService.validateResponse(errors, requestId);

        /*------------------- Validate database -------------------*/
        ApiResponseStatus apiResponseStatusDb = DatabaseValidation.validateTicket(reservationCode,
                flightCode, seats, ticketVietjetRepository);

        if(!ApiResponseStatus.SUCCESS.equals(apiResponseStatusDb)){
            LOGGER.info(Constant.FORMAT_LOG, apiResponseStatusDb.getStatusCode(), apiResponseStatusDb.getStatusMessage());
            return ResponseService.errorResponse(apiResponseStatusDb, requestId);
        }

        /*------------------- Validate Api -------------------*/
        String jwtResponse = ApiHttpClient.getToken(ApiUrls.AUTHENTICATION_URL,
                userAuthVietJet.getUserName(), userAuthVietJet.getPassWord());
        if (StringUtils.isEmpty(jwtResponse)) {
            return ResponseService.errorResponse(ApiResponseStatus.VIET_JET_API_ERROR, requestId);
        }

        String ticketResponse = ApiVietJet.callApiPassenger(jwtResponse, request);
        if (StringUtils.isEmpty(ticketResponse)) {
            return ResponseService.errorResponse(ApiResponseStatus.INVALID_TICKET, requestId);
        }


        TicKet ticKet = JsonUtils.fromJsonString(ticketResponse, TicKet.class);
        if(ticKet != null){
            /* Handle ticKet response and save */
            TicketVietJetInformation ticketVietjetInformation = handleTicketResponse(
                    ticKet, flightCode, reservationCode, seats);
            return ResponseService.successResponse(ApiResponseStatus.SUCCESS,ticketVietjetInformation, requestId);
        }

        return ResponseService.errorResponse(ApiResponseStatus.RESPONSE_API_ERROR, requestId);
    }

    /* Case 2: Service Scan Boarding pass */
    @Override
    public ResponseInfo<TicketVietJetInformation> checkScanTicketVietJet(TicketScanRequest request,
                                                                         BindingResult bindingResult) {
        String requestId = request.getRequestId();
        String flightCode = request.getFlightCode();
        String reservationCode = request.getReservationCode();
        String seats = request.getSeats();

        /*------------------- Validate ticket request -------------------*/
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            return ResponseService.validateResponse(errors, requestId);

        /*------------------- Validate Api -------------------*/
        String jwtResponse = ApiHttpClient.getToken(ApiUrls.AUTHENTICATION_URL,
                userAuthVietJet.getUserName(), userAuthVietJet.getPassWord());
        if (StringUtils.isEmpty(jwtResponse)) {
            return ResponseService.errorResponse(ApiResponseStatus.VIET_JET_API_ERROR, requestId);
        }

        String ticketResponse = ApiVietJet.callApiScanPassenger(jwtResponse, request);
        if (StringUtils.isEmpty(ticketResponse)) {
            return ResponseService.errorResponse(ApiResponseStatus.INVALID_TICKET, requestId);
        }

        /*-------------------  Validate database -------------------*/
        ApiResponseStatus apiResponseStatusDb = DatabaseValidation.validateTicket(reservationCode,
                flightCode, seats, ticketVietjetRepository);

        if(!ApiResponseStatus.SUCCESS.equals(apiResponseStatusDb)){
            LOGGER.info(Constant.FORMAT_LOG, apiResponseStatusDb.getStatusCode(), apiResponseStatusDb.getStatusMessage());
            return ResponseService.errorResponse(apiResponseStatusDb, requestId);
        }

        TicKet ticKet = JsonUtils.fromJsonString(ticketResponse, TicKet.class);
        if(ticKet != null){
           /* Handle ticKet response and save */
            TicketVietJetInformation ticketVietjetInformation = handleTicketResponse(
                    ticKet, flightCode, reservationCode, seats);
           return ResponseService.successResponse(ApiResponseStatus.SUCCESS,
                   ticketVietjetInformation, requestId);
        }

        return ResponseService.errorResponse(ApiResponseStatus.RESPONSE_API_ERROR, requestId);
    }

    /* Return ticket information and save database */
    private TicketVietJetInformation handleTicketResponse(TicKet ticKet, String flightCode,
                                                          String reservationCode, String seats){
        Passenger firstPassenger = ticKet.getPassengers().get(0);
        Journey firstJourney = ticKet.getJourneys().get(0);
        List<Charge> charges = ticKet.getCharges();

        String birthDate =   firstPassenger.getBirthDate();
        Date date = DateUtils.parseDate(birthDate);

        String flightTime =  String.valueOf(firstJourney.getFlightSegments().get(0).getScheduledDepartureLocalDatetime());
        Timestamp sqlFlightTime = DateUtils.parseTimestamp(flightTime);

        String firstName = firstPassenger.getFirstName();
        String lastName = firstPassenger.getLastName();

        Double totalAmount = charges.stream()
                                    .filter(c -> Constant.AMOUNT_FA.equals(c.getChargeCode()))
                                    .mapToDouble(c -> c.getCurrencyAmounts().get(0).getTotalAmount())
                                    .sum();

        /* Save database */
        boolean exists = ticketVietjetRepository.checkSaveTicket(reservationCode, flightCode, seats);
        if (!exists) {
            TicketVietJet ticketVietJet = TicketVietJet.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .birthDate(date)
                    .flightTime(sqlFlightTime)
                    .flightCode(flightCode)
                    .reservationCode(reservationCode)
                    .seats(seats)
                    .totalAmount(totalAmount)
                    .build();
            ticketVietjetRepository.createTicket(ticketVietJet);
        }

        String ticketId = ticketVietjetRepository.getTicketId(reservationCode, flightCode, seats);

        return new TicketVietJetInformation(
                ticketId,
                StringUtils.join(lastName, StringUtils.SPACE, firstName),
                birthDate, flightTime, totalAmount
        );
    }

}