package vn.com.hdbank.boardingpasshdbank.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;

import java.io.IOException;

public class ApiHttpClient {
    private static ApiHttpClient instance;
    private final CloseableHttpClient httpClient;

    private ApiHttpClient() {
        this.httpClient = HttpClients.createDefault();
    }

    public static synchronized ApiHttpClient getInstance() {
        if (instance == null) {
            instance = new ApiHttpClient();
        }
        return instance;
    }

    public String getToken(String url, String username, String password) throws IOException {
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json");

        String jsonBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        request.setEntity(new StringEntity(jsonBody));

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_FORBIDDEN){
                throw new CustomException(ApiResponseStatus.FORBIDDEN);
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        }

        return null;
    }

    public String executeGetRequest(String url, String jwt) throws IOException {
        HttpUriRequest request = new HttpGet(url);
        request.setHeader("Authorization", "Bearer " + jwt);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        }

        return null;
    }

    public String executePostRequest(String url, String jwt, String requestBody) throws IOException {
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Authorization", "Bearer " + jwt);

        StringEntity entity = new StringEntity(requestBody);
        request.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                return EntityUtils.toString(responseEntity);
            }
        }

        return null;
    }

    public void close() throws IOException {
        httpClient.close();
    }
}
