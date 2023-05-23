package vn.com.hdbank.boardingpasshdbank.service.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import vn.com.hdbank.boardingpasshdbank.common.ApiUrls;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketScanRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.response.ResponseToken;
import vn.com.hdbank.boardingpasshdbank.utils.ApiHttpClient;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;

import java.net.URI;
import java.net.URISyntaxException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiVietJet {
    public static String callApiPassenger(String jwtResponse, TicketRequest request) {
        try {
            URI passengerVietjetUri = new URIBuilder(ApiUrls.PASSENGER_VIET_JET_URL)
                    .addParameter("reservationLocator", request.getReservationCode())
                    .addParameter("airlineCode", request.getAirlineCode())
                    .addParameter("flightNumber", request.getFlightNumber())
                    .addParameter("seatRow", request.getSeatRow())
                    .addParameter("seatCols", request.getSeatCols())
                    .build();
            String jwt = JsonUtils.fromJsonString(jwtResponse, ResponseToken.class).getToken();
            return ApiHttpClient.executeGetRequest(passengerVietjetUri.toString(), jwt);
        } catch (URISyntaxException e) {
            return StringUtils.EMPTY;
        }
    }

    public static String callApiScanPassenger(String jwtResponse, TicketScanRequest request) {
        try {
            URI passengerVietjetUri = new URIBuilder(ApiUrls.PASSENGER_VIET_JET_URL)
                    .addParameter("reservationLocator", request.getReservationCode())
                    .addParameter("airlineCode", request.getAirlineCode())
                    .addParameter("flightNumber", request.getFlightNumber())
                    .addParameter("seatRow", request.getSeatRow())
                    .addParameter("seatCols", request.getSeatCols())
                    .addParameter("passengerFirstName", request.getLastName())
                    .addParameter("passengerLastName", request.getFistName())
                    .build();
            String jwt = JsonUtils.fromJsonString(jwtResponse, ResponseToken.class).getToken();
            return ApiHttpClient.executeGetRequest(passengerVietjetUri.toString(), jwt);
        } catch (URISyntaxException e) {
            return StringUtils.EMPTY;
        }
    }
}
