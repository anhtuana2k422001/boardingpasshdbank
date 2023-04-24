package com.hoanhtuan.boardingpasshdbank.service.impl;

import com.google.gson.Gson;
import com.hoanhtuan.boardingpasshdbank.common.ApiUrls;
import com.hoanhtuan.boardingpasshdbank.model.CustomerTicketInformation;
import com.hoanhtuan.boardingpasshdbank.model.UserAuth;
import com.hoanhtuan.boardingpasshdbank.model.response.ResponseToken;
import com.hoanhtuan.boardingpasshdbank.model.vietjet.Charge;
import com.hoanhtuan.boardingpasshdbank.model.vietjet.Journey;
import com.hoanhtuan.boardingpasshdbank.model.vietjet.Passenger;
import com.hoanhtuan.boardingpasshdbank.model.vietjet.TicKet;
import com.hoanhtuan.boardingpasshdbank.service.TicketVietJetService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class TicketVietJetServiceImpl implements TicketVietJetService {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    String authenticationUrl = ApiUrls.AUTHENTICATION_URL;
    String passengerVietJetUrl = ApiUrls.PASSENGER_VIET_JET_URL;

    @Override
    public CustomerTicketInformation checkPassengerVietJet(String fullName, String flightCode, String reservationCode, String seats) throws IOException {
        String airlineCode = flightCode.substring(0,2);
        String flightNumber = flightCode.substring(2);
        String seatRow = seats.substring(0,seats.length()-1);
        String seatCols = seats.substring(seats.length()-1);

        CustomerTicketInformation customerTicketInformation = new CustomerTicketInformation();
        CloseableHttpResponse authenticationResponse = getResponseTokenResponseEntity();
        if (authenticationResponse.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            String responseBody = EntityUtils.toString(authenticationResponse.getEntity());
            Gson gson = new Gson();
            ResponseToken responseToken =  gson.fromJson(responseBody, ResponseToken.class);
            String token = "Bearer:" + responseToken.getToken();
            // Use Token to get Response
            HttpGet httpGet;
            if(fullName != null){
                String[] names = fullName.split("/");
                String passengerLastName = names[0];
                String passengerFirstName = names[1];
                String encodedFirstName = URLEncoder.encode(passengerFirstName, StandardCharsets.UTF_8);
                httpGet = new HttpGet(passengerVietJetUrl + "?reservationLocator=" + reservationCode + "&passengerLastName=" + passengerLastName + "&passengerFirstName=" + encodedFirstName + "&airlineCode=" + airlineCode +"&flightNumber="+ flightNumber+"&seatRow="+seatRow+"&seatCols="+seatCols);
            }
            else{
                httpGet = new HttpGet(passengerVietJetUrl + "?reservationLocator=" + reservationCode +"&airlineCode=" + airlineCode +"&flightNumber="+ flightNumber+"&seatRow="+seatRow+"&seatCols="+seatCols);
            }
            httpGet.addHeader("Authorization", token);
            httpGet.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            httpGet.addHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseStr = EntityUtils.toString(response.getEntity());
                if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                    TicKet ticKet =  gson.fromJson(responseStr, TicKet.class);
                    List<Passenger> lstPassenger = ticKet.getPassengers();
                    List<Charge> lstCharge = ticKet.getCharges();
                    List<Journey> lstJourney = ticKet.getJourneys();
                    customerTicketInformation.setFullName(lstPassenger.get(0).getLastName() + " " +
                            lstPassenger.get(0).getFirstName());
                    customerTicketInformation.setBirthDate(lstPassenger.get(0).getBirthDate());
                    customerTicketInformation.setFlightTime(String.valueOf(lstJourney.get(0).getFlightSegments()
                            .get(0).getScheduledDepartureLocalDatetime()));
                    Double totalAmount = lstCharge.stream()
                            .filter(c->c.getChargeCode().equals("FA"))
                            .mapToDouble(c->c.getCurrencyAmounts().get(0).getTotalAmount())
                            .sum();
                    customerTicketInformation.setTotalAmount(totalAmount);

                    return  customerTicketInformation;
                } else {
                    throw new IOException("Failed to get passenger information from Viet-jet: " + response.getStatusLine().getReasonPhrase());
                }
            }
        } else {
            throw new IOException("Failed to authenticate user: " + authenticationResponse.getStatusLine().getReasonPhrase());
        }
    }


    // Authentication
    private CloseableHttpResponse getResponseTokenResponseEntity() throws IOException {
        UserAuth authenticationUser = getAuthenticationUser();
        String authenticationBody = getBodyAuth(authenticationUser);
        HttpPost httpPost = new HttpPost(authenticationUrl);
        httpPost.setEntity(new StringEntity(authenticationBody));
        httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpPost.setHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
        return httpClient.execute(httpPost);
    }

    // Info login user
    private UserAuth getAuthenticationUser() {
        UserAuth user = new UserAuth();
        user.setUserName("VJAPIHDBankCard");
        user.setPassWord("Hochiminh&789");
        return user;
    }

    //Convert the UserAuth object to a JSON string.
    private String getBodyAuth(final UserAuth user) {
        return new Gson().toJson(user);
    }
}
