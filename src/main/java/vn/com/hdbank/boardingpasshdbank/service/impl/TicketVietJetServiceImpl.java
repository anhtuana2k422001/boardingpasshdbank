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
import vn.com.hdbank.boardingpasshdbank.utils.ApiHttpClient;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;

@Service
public class TicketVietJetServiceImpl {

    private ApiHttpClient apiHttpClient = ApiHttpClient.getInstance();
    private JsonUtils jsonUtils = JsonUtils.getInstance();
    @Value("${auth.username}")
    private String authUsername;
    @Value("${auth.password}")
    private String authPass;


    public CustomerTicketInformation checkPassengerVietJet(ReservationRequestDTO reservationRequestDTO) {

        try {

            //get JWT
            String jwtResponse = apiHttpClient.getToken(ApiUrls.AUTHENTICATION_URL, authUsername, authPass);
            System.out.println(jwtResponse);

            if (jwtResponse != null) {
                URI passengerVietjetUri = new URIBuilder(ApiUrls.PASSENGER_VIET_JET_URL)
                        .addParameter("reservationLocator", reservationRequestDTO.getReservationCode())
                        .build();

                String jwt = jsonUtils.fromJsonString(jwtResponse,ResponseToken.class).getToken();

                String ticketResponse = apiHttpClient.executeGetRequest(passengerVietjetUri.toString(), jwt);
                System.out.println(ticketResponse);

                if (ticketResponse != null) {
                    TicKet ticKet = jsonUtils.fromJsonString(ticketResponse, TicKet.class);

                    ArrayList<Passenger> lstPassenger = ticKet.getPassengers();
                    ArrayList<Charge> lstCharge = ticKet.getCharges();
                    ArrayList<Journey> lstJourney = ticKet.getJourneys();
                    CustomerTicketInformation customerTicketInformation = new CustomerTicketInformation();
                    customerTicketInformation.setFullName(lstPassenger.get(0).getLastName() + " " +
                            lstPassenger.get(0).getFirstName());
                    customerTicketInformation.setBirthDate(lstPassenger.get(0).getBirthDate());
                    customerTicketInformation.setFlightTime(String.valueOf(lstJourney.get(0).getFlightSegments()
                            .get(0).getScheduledDepartureLocalDatetime()));
                    Double totalAmount = lstCharge.stream()
                            .filter(c -> c.getChargeCode().equals("FA"))
                            .mapToDouble(c -> c.getCurrencyAmounts().get(0).getTotalAmount())
                            .sum();
                    customerTicketInformation.setTotalAmount(totalAmount);
                    return customerTicketInformation;
                } else {
                    throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
                }
            }else {
                throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
