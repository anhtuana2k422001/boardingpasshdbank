package vn.com.hdbank.boardingpasshdbank.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ApiHttpClient {
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final ApiResponseStatus status = ApiResponseStatus.EXTERNAL_API_ERROR;

    public static String getToken(String url, String username, String password) {
        try {
            HttpPost request = new HttpPost(url);
            request.setHeader("Content-Type", "application/json");

            String jsonBody = createJsonBody(username, password);
            if(StringUtils.isEmpty(jsonBody)){
                LOGGER.info(Constant.FORMAT_LOG, status.getStatusCode(), status.getStatusMessage());
                return StringUtils.EMPTY;
            }

            request.setEntity(new StringEntity(jsonBody));
            LOGGER.info(Constant.OUTGOING_REQUEST, LogUtils.logHttpPost(request));   /* Log request */

            CloseableHttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_FORBIDDEN) {
                LOGGER.info(Constant.FORMAT_LOG, status.getStatusCode(), status.getStatusMessage());
                return StringUtils.EMPTY;
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                LOGGER.info(Constant.OUTGOING_RESPONSE, StringUtils.deleteWhitespace(responseString));  /* Log response */
                return responseString;
            }

            return null;

        } catch (Exception ex) {
            LOGGER.info(Constant.FORMAT_LOG, status.getStatusCode(), status.getStatusMessage());
            return StringUtils.EMPTY;
        }
    }

    public static String createJsonBody(String username, String password) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(Map.of("username", username, "password", password));
        } catch (Exception e) {
            LOGGER.info(Constant.FORMAT_LOG, status.getStatusCode(), status.getStatusMessage());
            return StringUtils.EMPTY;
        }
    }

    public static String executeGetRequest(String url, String jwt) {
        try {
            HttpGet request = new HttpGet(url);
            request.setHeader("Authorization", "Bearer " + jwt);
            LOGGER.info(Constant.OUTGOING_REQUEST, LogUtils.logHttpGet(request));   /* Log request */

            CloseableHttpResponse response = httpClient.execute(request);

            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String responseString = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                LOGGER.info(Constant.OUTGOING_RESPONSE, StringUtils.deleteWhitespace(responseString));   /* Log response */
                return responseString;
            }

            return null;

        } catch (Exception ex) {
            LOGGER.info(Constant.FORMAT_LOG, status.getStatusCode(), status.getStatusMessage());
            return StringUtils.EMPTY;
        }
    }

    public static String executePostRequest(String url, String jwt, String requestBody) {
        try {
            HttpPost request = new HttpPost(url);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer " + jwt);

            StringEntity entity = new StringEntity(requestBody);
            request.setEntity(entity);
            LOGGER.info(Constant.OUTGOING_REQUEST, LogUtils.logHttpPost(request));   /* Log request */

            CloseableHttpResponse response = httpClient.execute(request);

            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String responseString = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                LOGGER.info(Constant.OUTGOING_RESPONSE, StringUtils.deleteWhitespace(responseString));   /* Log response */
                return responseString;
            }

            return null;

        } catch (Exception ex) {
            LOGGER.info(Constant.FORMAT_LOG, status.getStatusCode(), status.getStatusMessage());
            return StringUtils.EMPTY;
        }
    }

}
