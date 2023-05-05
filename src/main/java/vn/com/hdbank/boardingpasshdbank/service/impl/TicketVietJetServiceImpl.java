package vn.com.hdbank.boardingpasshdbank.service.impl;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.ApiUrls;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseToken;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.CustomerTicketInformation;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.ReservationRequestDTO;
import vn.com.hdbank.boardingpasshdbank.model.vietjetResponse.Charge;
import vn.com.hdbank.boardingpasshdbank.model.vietjetResponse.Journey;
import vn.com.hdbank.boardingpasshdbank.model.vietjetResponse.Passenger;
import vn.com.hdbank.boardingpasshdbank.model.vietjetResponse.TicKet;
import vn.com.hdbank.boardingpasshdbank.service.BaseService;
import vn.com.hdbank.boardingpasshdbank.utils.ApiHttpClient;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketVietJetServiceImpl extends BaseService {
    @Value("${auth.username}")
    private String authUsername;
    @Value("${auth.password}")
    private String authPass;


    public CustomerTicketInformation checkPassengerVietJet(ReservationRequestDTO reservationRequestDTO) {
        CustomerTicketInformation customerTicketInformation = null;
        try {
            //get JWT
            String jwtResponse = apiHttpClient.getToken(ApiUrls.AUTHENTICATION_URL, authUsername, authPass);
            System.out.println(jwtResponse);
            if (jwtResponse != null) {
                URI passengerVietjetUri = new URIBuilder(ApiUrls.PASSENGER_VIET_JET_URL)
                        .addParameter("reservationLocator", reservationRequestDTO.getReservationCode())
                        .addParameter("airlineCode", reservationRequestDTO.getAirlineCode())
                        .addParameter("flightNumber", reservationRequestDTO.getFlightNumber())
                        .addParameter("seatRow", reservationRequestDTO.getSeatRow())
                        .addParameter("seatCols", reservationRequestDTO.getSeatCols())
                        .build();
                String jwt = jsonUtils.fromJsonString(jwtResponse, ResponseToken.class).getToken();
                String ticketResponse = apiHttpClient.executeGetRequest(passengerVietjetUri.toString(), jwt);
                System.out.println(ticketResponse);
                if (ticketResponse != null) {
                    customerTicketInformation = mapTicketResponseToCustomerTicketInformation(ticketResponse);
                } else {
                    throw new CustomException(ApiResponseStatus.BAD_REQUEST);
                }
            }

        } catch (Exception e) {
            if (e instanceof CustomException) {
                throw new CustomException(((CustomException) e).getApiResponseStatus());
            }
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }

        return customerTicketInformation;
    }

    private CustomerTicketInformation mapTicketResponseToCustomerTicketInformation(String ticketResponse) throws IOException {
        TicKet ticKet = jsonUtils.fromJsonString(ticketResponse, TicKet.class);
        Passenger firstPassenger = ticKet.getPassengers().get(0);
        Journey firstJourney = ticKet.getJourneys().get(0);
        List<Charge> charges = ticKet.getCharges();
        Double totalAmount = charges.stream()
                .filter(c -> "FA".equals(c.getChargeCode()))
                .mapToDouble(c -> c.getCurrencyAmounts().get(0).getTotalAmount())
                .sum();
        return new CustomerTicketInformation(
                firstPassenger.getLastName() + " " + firstPassenger.getFirstName(),
                firstPassenger.getBirthDate(),
                String.valueOf(firstJourney.getFlightSegments().get(0).getScheduledDepartureLocalDatetime()),
                totalAmount
        );
    }


}