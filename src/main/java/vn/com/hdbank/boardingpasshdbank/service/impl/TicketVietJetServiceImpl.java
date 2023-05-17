package vn.com.hdbank.boardingpasshdbank.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.ApiUrls;
import vn.com.hdbank.boardingpasshdbank.common.ResponseEntityHelper;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketScanRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.response.*;
import vn.com.hdbank.boardingpasshdbank.model.response.TicketVietjetInformation;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketRequest;
import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietjet;
import vn.com.hdbank.boardingpasshdbank.service.BaseService;
import org.springframework.stereotype.Service;
import vn.com.hdbank.boardingpasshdbank.utils.ApiHttpClient;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;

import java.net.URI;
import java.util.List;

@Service
public class TicketVietJetServiceImpl extends BaseService {
    private static final Logger LOGGER  = LoggerFactory.getLogger(TicketVietJetServiceImpl.class);
    @Autowired
    private UserAuthVietJet userAuthVietJet;

    // Case 1: Service Self-entering information
    public ResponseEntity<ResponseInfo<TicketVietjetInformation>> checkTicketVietJet(TicketRequest request) {
        try {
            List<TicketVietjet> ticketVietjetList = ticketVietjetRepository.findByFlightCodeAndPassengerIdIsNotNull(request.getFlightCode());
            if (!ticketVietjetList.isEmpty()) {
                LOGGER.info(ApiResponseStatus.TICKET_VIETJET_EXISTED_AND_ASSIGNED.getStatusMessage());
                return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.TICKET_VIETJET_EXISTED_AND_ASSIGNED);
            }
            String jwtResponse = ApiHttpClient.getToken(ApiUrls.AUTHENTICATION_URL, userAuthVietJet.getUserName(), userAuthVietJet.getPassWord());
            if (StringUtils.isEmpty(jwtResponse)) {
                LOGGER.info(ApiResponseStatus.VIETJET_API_ERROR.getStatusMessage());
                return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.VIETJET_API_ERROR);
            }
            URI passengerVietjetUri = new URIBuilder(ApiUrls.PASSENGER_VIET_JET_URL)
                    .addParameter("reservationLocator", request.getReservationCode())
                    .addParameter("airlineCode", request.getAirlineCode())
                    .addParameter("flightNumber", request.getFlightNumber())
                    .addParameter("seatRow", request.getSeatRow())
                    .addParameter("seatCols", request.getSeatCols()).build();
            String jwt = JsonUtils.fromJsonString(jwtResponse, ResponseToken.class).getToken();
            String ticketResponse = ApiHttpClient.executeGetRequest(passengerVietjetUri.toString(), jwt);
            if (StringUtils.isEmpty(ticketResponse)) {
                LOGGER.info(ApiResponseStatus.INVALID_TICKET.getStatusMessage());
                return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.INVALID_TICKET);
            }
            /* Handle response and save */
            TicketVietjetInformation ticketVietjetInformation = mapTicketRequest(ticketResponse,
                    request.getFlightCode(),
                    request.getReservationCode(),
                    request.getSeats()
            );
            return  ResponseEntityHelper.successResponseEntity(ticketVietjetInformation);
        }catch (Exception e) {
            LOGGER.error(ApiResponseStatus.INTERNAL_SERVER_ERROR.getStatusMessage());
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Case 2: Service Scan Boarding pass
    public ResponseEntity<ResponseInfo<TicketVietjetInformation>> checkScanTicketVietJet(TicketScanRequest request) {
        try {
            List<TicketVietjet> ticketVietjetList = ticketVietjetRepository.findByFlightCodeAndPassengerIdIsNotNull(request.getFlightCode());
            if (!ticketVietjetList.isEmpty()) {
                LOGGER.info(ApiResponseStatus.TICKET_VIETJET_EXISTED_AND_ASSIGNED.getStatusMessage());
                return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.TICKET_VIETJET_EXISTED_AND_ASSIGNED);
            }
            String jwtResponse = ApiHttpClient.getToken(ApiUrls.AUTHENTICATION_URL, userAuthVietJet.getUserName(), userAuthVietJet.getPassWord());
            if (StringUtils.isEmpty(jwtResponse)) {
                LOGGER.info(ApiResponseStatus.VIETJET_API_ERROR.getStatusMessage());
                return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.VIETJET_API_ERROR);
            }
            URI passengerVietjetUri = new URIBuilder(ApiUrls.PASSENGER_VIET_JET_URL)
                    .addParameter("reservationLocator", request.getReservationCode())
                    .addParameter("airlineCode", request.getAirlineCode())
                    .addParameter("flightNumber", request.getFlightNumber())
                    .addParameter("seatRow", request.getSeatRow())
                    .addParameter("seatCols", request.getSeatCols())
                    .addParameter("passengerFirstName", request.getFistName())
                    .addParameter("passengerLastName", request.getLastName())
                    .build();
            String jwt = JsonUtils.fromJsonString(jwtResponse, ResponseToken.class).getToken();
            String ticketResponse = ApiHttpClient.executeGetRequest(passengerVietjetUri.toString(), jwt);
            if (StringUtils.isEmpty(ticketResponse)) {
                LOGGER.info(ApiResponseStatus.INVALID_TICKET.getStatusMessage());
                return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.INVALID_TICKET);
            }
            /* Handle response and save */
            TicketVietjetInformation ticketVietjetInformation = mapTicketRequest(ticketResponse,
                    request.getFlightCode(),
                    request.getReservationCode(),
                    request.getSeats()
            );
            return  ResponseEntityHelper.successResponseEntity(ticketVietjetInformation);
        } catch (Exception e) {
            LOGGER.error(ApiResponseStatus.INTERNAL_SERVER_ERROR.getStatusMessage());
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* Return ticket information and save database */
    private TicketVietjetInformation mapTicketRequest(String ticketResponse, String  flightCode,
                                                      String reservationCode, String seats) {
        TicKet ticKet = JsonUtils.fromJsonString(ticketResponse, TicKet.class);
        Passenger firstPassenger = ticKet.getPassengers().get(0);
        Journey firstJourney = ticKet.getJourneys().get(0);
        List<Charge> charges = ticKet.getCharges();
        Double totalAmount = charges.stream().filter(c -> "FA"
                .equals(c.getChargeCode())).mapToDouble(c -> c.getCurrencyAmounts()
                .get(0).getTotalAmount()).sum();
        /* Save database */
        if (!ticketVietjetRepository.checkExistsByFlightCode(flightCode)) {
            TicketVietjet saveTicket = new TicketVietjet(firstPassenger.getFirstName(), firstPassenger.getLastName(), flightCode, reservationCode, seats);
            ticketVietjetRepository.create(saveTicket);
        }
        return new TicketVietjetInformation(
                StringUtils.join(firstPassenger.getLastName()," " ,firstPassenger.getFirstName()),
                firstPassenger.getBirthDate(),
                String.valueOf(firstJourney.getFlightSegments().get(0).getScheduledDepartureLocalDatetime()),
                totalAmount
        );
    }

}