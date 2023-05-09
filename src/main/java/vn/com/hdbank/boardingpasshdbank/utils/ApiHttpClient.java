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

public class ApiHttpClient {

    private static final String CLASS_NAME = ApiHttpClient.class.getSimpleName();

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    private ApiHttpClient() {
    }


    public static String getToken(String url, String username, String password) {
        try {
            HttpPost request = new HttpPost(url);
            request.setHeader("Content-Type", "application/json");

            String jsonBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
            request.setEntity(new StringEntity(jsonBody));

            CloseableHttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_FORBIDDEN) {
                WriteLog.errorLog(CLASS_NAME, "getToken", "Forbidden");
                throw new CustomException(ApiResponseStatus.EXTERNAL_API_ERROR);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (Exception ex) {
            throw new CustomException(ApiResponseStatus.EXTERNAL_API_ERROR);
        }

        return null;
    }

    public static String executeGetRequest(String url, String jwt) {
        try {
            HttpUriRequest request = new HttpGet(url);
            request.setHeader("Authorization", "Bearer " + jwt);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity);
                }
            }
        } catch (Exception ex) {
            throw new CustomException(ApiResponseStatus.EXTERNAL_API_ERROR);
        }


        return null;
    }

    public static String executePostRequest(String url, String jwt, String requestBody) {
        try {
            HttpPost request = new HttpPost(url);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer " + jwt);

            StringEntity entity = new StringEntity(requestBody);
            request.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                return EntityUtils.toString(responseEntity);
            }
        } catch (Exception ex) {
            throw new CustomException(ApiResponseStatus.EXTERNAL_API_ERROR);
        }


        return null;
    }

}
