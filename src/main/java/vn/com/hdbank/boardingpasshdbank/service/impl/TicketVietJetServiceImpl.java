package vn.com.hdbank.boardingpasshdbank.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.ApiUrls;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.response.ResponseToken;
import vn.com.hdbank.boardingpasshdbank.model.response.TicketVietjetInformation;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketRequest;
import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietjet;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.response.Charge;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.response.Journey;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.response.Passenger;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.response.TicKet;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietjetRepository;
import vn.com.hdbank.boardingpasshdbank.service.BaseService;
import org.springframework.stereotype.Service;
import vn.com.hdbank.boardingpasshdbank.utils.ApiHttpClient;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Service
public class TicketVietJetServiceImpl extends BaseService {
    @Autowired
    private CustomerRepository passengerRepository;
    @Autowired
    private TicketVietjetRepository ticketVietjetRepository;

    @Value("${auth.username}")
    private String authUsername;
    @Value("${auth.password}")
    private String authPass;

    public TicketVietjetInformation checkPassengerVietJet(TicketRequest ticketRequest) {
        TicketVietjetInformation ticketVietjetInformation = null;
        try {
            List<TicketVietjet> lstFindByFlightCodeAndPassengerIdIsNotNull = ticketVietjetRepository.findByFlightCodeAndPassengerIdIsNotNull(ticketRequest.getFlightCode());
            if (lstFindByFlightCodeAndPassengerIdIsNotNull.size() > 0) {
                throw new CustomException(ApiResponseStatus.TICKET_VIETJET_EXISTED_AND_ASSIGNED);
            }

            String jwtResponse = ApiHttpClient.getToken(ApiUrls.AUTHENTICATION_URL, authUsername, authPass);
            if (StringUtils.equals(jwtResponse, "")) {
                throw new CustomException(ApiResponseStatus.VIETJET_API_ERROR);
            }

            URI passengerVietjetUri = new URIBuilder(ApiUrls.PASSENGER_VIET_JET_URL)
                    .addParameter("reservationLocator", ticketRequest.getReservationCode())
                    .addParameter("airlineCode", ticketRequest.getAirlineCode())
                    .addParameter("flightNumber", ticketRequest.getFlightNumber())
                    .addParameter("seatRow", ticketRequest.getSeatRow())
                    .addParameter("seatCols", ticketRequest.getSeatCols()).build();
            if(StringUtils.isNotEmpty(ticketRequest.getFirstName()) && StringUtils.isNotEmpty(ticketRequest.getLastName()) ){
                passengerVietjetUri = new URIBuilder(passengerVietjetUri)
                        .addParameter("passengerFirstName", ticketRequest.getFirstName())
                        .addParameter("passengerLastName", ticketRequest.getLastName())
                        .build();
            }

            String jwt = JsonUtils.fromJsonString(jwtResponse, ResponseToken.class).getToken();
            String ticketResponse = ApiHttpClient.executeGetRequest(passengerVietjetUri.toString(), jwt);

            if (StringUtils.equals(ticketResponse, "")) {
                throw new CustomException(ApiResponseStatus.INVALID_TICKET);
            }

            ticketVietjetInformation = mapTicketResponseToTicketVietjetInformationAndSaveDB(ticketResponse,ticketRequest);
        } catch (Exception e) {
            if (e instanceof CustomException) {
                throw new CustomException(((CustomException) e).getApiResponseStatus());
            }
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return ticketVietjetInformation;
    }

    private TicketVietjetInformation mapTicketResponseToTicketVietjetInformationAndSaveDB(String ticketResponse,TicketRequest ticketRequest) throws IOException {
        TicKet ticKet = JsonUtils.fromJsonString(ticketResponse, TicKet.class);
        Passenger firstPassenger = ticKet.getPassengers().get(0);
        Journey firstJourney = ticKet.getJourneys().get(0);
        List<Charge> charges = ticKet.getCharges();
        Double totalAmount = charges.stream().filter(c -> "FA".equals(c.getChargeCode())).mapToDouble(c -> c.getCurrencyAmounts().get(0).getTotalAmount()).sum();
        //save db
        if(!ticketVietjetRepository.checkExistsByFlightCode(ticketRequest.getFlightCode())){
            TicketVietjet saveTicket = new TicketVietjet(firstPassenger.getFirstName(), firstPassenger.getLastName(), ticketRequest.getFlightCode(), ticketRequest.getReservationCode(), ticketRequest.getSeats());
            ticketVietjetRepository.create(saveTicket);
        }
        return new TicketVietjetInformation(firstPassenger.getLastName() + " " + firstPassenger.getFirstName(), firstPassenger.getBirthDate(), String.valueOf(firstJourney.getFlightSegments().get(0).getScheduledDepartureLocalDatetime()), totalAmount);


    }


}