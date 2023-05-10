package vn.com.hdbank.boardingpasshdbank.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.ApiUrls;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseToken;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.entity.Passenger;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.PassengerVietjetInformationDTO;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.ReservationRequestDTO;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.entity.TicketVietjet;
import vn.com.hdbank.boardingpasshdbank.model.vietjetResponse.Charge;
import vn.com.hdbank.boardingpasshdbank.model.vietjetResponse.Journey;
import vn.com.hdbank.boardingpasshdbank.model.vietjetResponse.TicKet;
import vn.com.hdbank.boardingpasshdbank.repository.PassengerRepository;
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
    private PassengerRepository passengerRepository;
    @Autowired
    private TicketVietjetRepository ticketVietjetRepository;

    @Value("${auth.username}")
    private String authUsername;
    @Value("${auth.password}")
    private String authPass;

    public PassengerVietjetInformationDTO checkPassengerVietJet(ReservationRequestDTO reservationRequestDTO) {
        PassengerVietjetInformationDTO passengerVietjetInformationDTO = null;
        try {
            List<TicketVietjet> lstFindByFlightCodeAndPassengerIdIsNotNull = ticketVietjetRepository.findByFlightCodeAndPassengerIdIsNotNull(reservationRequestDTO.getFlightCode());
            if (lstFindByFlightCodeAndPassengerIdIsNotNull.size() > 0) {
                throw new CustomException(ApiResponseStatus.TICKET_VIETJET_EXISTED_AND_ASSIGNED);
            }

            String jwtResponse = ApiHttpClient.getToken(ApiUrls.AUTHENTICATION_URL, authUsername, authPass);
            if (StringUtils.equals(jwtResponse, "")) {
                throw new CustomException(ApiResponseStatus.VIETJET_API_ERROR);
            }

            URI passengerVietjetUri = new URIBuilder(ApiUrls.PASSENGER_VIET_JET_URL)
                    .addParameter("reservationLocator", reservationRequestDTO.getReservationCode())
                    .addParameter("airlineCode", reservationRequestDTO.getAirlineCode())
                    .addParameter("flightNumber", reservationRequestDTO.getFlightNumber())
                    .addParameter("seatRow", reservationRequestDTO.getSeatRow())
                    .addParameter("seatCols", reservationRequestDTO.getSeatCols()).build();
            if(StringUtils.isNotEmpty(reservationRequestDTO.getFirstName()) && StringUtils.isNotEmpty(reservationRequestDTO.getLastName()) ){
                passengerVietjetUri = new URIBuilder(passengerVietjetUri)
                        .addParameter("passengerFirstName", reservationRequestDTO.getFirstName())
                        .addParameter("passengerLastName", reservationRequestDTO.getLastName())
                        .build();
            }

            String jwt = JsonUtils.fromJsonString(jwtResponse, ResponseToken.class).getToken();
            String ticketResponse = ApiHttpClient.executeGetRequest(passengerVietjetUri.toString(), jwt);

            if (StringUtils.equals(ticketResponse, "")) {
                throw new CustomException(ApiResponseStatus.INVALID_TICKET);
            }

            passengerVietjetInformationDTO = mapTicketResponseToCustomerTicketInformationDTO(ticketResponse);

        } catch (Exception e) {
            if (e instanceof CustomException) {
                throw new CustomException(((CustomException) e).getApiResponseStatus());
            }
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return passengerVietjetInformationDTO;
    }

    private PassengerVietjetInformationDTO mapTicketResponseToCustomerTicketInformationDTO(String ticketResponse) throws IOException {
        TicKet ticKet = JsonUtils.fromJsonString(ticketResponse, TicKet.class);
        vn.com.hdbank.boardingpasshdbank.model.vietjetResponse.Passenger firstPassenger = ticKet.getPassengers().get(0);
        Journey firstJourney = ticKet.getJourneys().get(0);
        List<Charge> charges = ticKet.getCharges();
        Double totalAmount = charges.stream().filter(c -> "FA".equals(c.getChargeCode())).mapToDouble(c -> c.getCurrencyAmounts().get(0).getTotalAmount()).sum();
        return new PassengerVietjetInformationDTO(firstPassenger.getLastName() + " " + firstPassenger.getFirstName(), firstPassenger.getBirthDate(), String.valueOf(firstJourney.getFlightSegments().get(0).getScheduledDepartureLocalDatetime()), totalAmount);
    }


}