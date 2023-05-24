package vn.com.hdbank.boardingpasshdbank.service.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import vn.com.hdbank.boardingpasshdbank.common.ApiUrls;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
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
                    .addParameter(Constant.RESERVATION_LOCATOR, request.getReservationCode())
                    .addParameter(Constant.AIRLINE_CODE, request.getAirlineCode())
                    .addParameter(Constant.FLIGHT_NUMBER, request.getFlightNumber())
                    .addParameter(Constant.SEAT_ROW, request.getSeatRow())
                    .addParameter(Constant.SEAT_COLS, request.getSeatCols())
                    .build();
            ResponseToken responseToken = JsonUtils.fromJsonString(jwtResponse, ResponseToken.class);
            if(responseToken != null){
                String jwt = responseToken.getToken();
                return ApiHttpClient.executeGetRequest(passengerVietjetUri.toString(), jwt);
            }
            return null;
        } catch (URISyntaxException e) {
            return StringUtils.EMPTY;
        }
    }

    public static String callApiScanPassenger(String jwtResponse, TicketScanRequest request) {
        try {
            URI passengerVietjetUri = new URIBuilder(ApiUrls.PASSENGER_VIET_JET_URL)
                    .addParameter(Constant.RESERVATION_LOCATOR, request.getReservationCode())
                    .addParameter(Constant.AIRLINE_CODE, request.getAirlineCode())
                    .addParameter(Constant.FLIGHT_NUMBER, request.getFlightNumber())
                    .addParameter(Constant.SEAT_ROW, request.getSeatRow())
                    .addParameter(Constant.SEAT_COLS, request.getSeatCols())
                    .addParameter(Constant.PASSENGER_LAST_NAME, request.getLastName())
                    .addParameter(Constant.PASSENGER_FIRST_NAME, request.getFistName())
                    .build();
            ResponseToken responseToken = JsonUtils.fromJsonString(jwtResponse, ResponseToken.class);
            if(responseToken != null){
                String jwt = responseToken.getToken();
                return ApiHttpClient.executeGetRequest(passengerVietjetUri.toString(), jwt);
            }
            return null;
        } catch (URISyntaxException e) {
            return StringUtils.EMPTY;
        }
    }
}
