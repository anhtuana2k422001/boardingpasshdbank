package vn.com.hdbank.boardingpasshdbank.service.validate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.ApiUrls;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketScanRequest;
import vn.com.hdbank.boardingpasshdbank.service.utils.ApiVietJet;
import vn.com.hdbank.boardingpasshdbank.utils.ApiHttpClient;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiVietJetValidation {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiVietJetValidation.class);
    public static ApiResponseStatus validateApiTickKet(TicketRequest request, String authUserName, String authPassword){
        String jwtResponse = ApiHttpClient.getToken(
                ApiUrls.AUTHENTICATION_URL,
                authUserName,
                authPassword
        );
        if (StringUtils.isEmpty(jwtResponse)) {
            LOGGER.info(ApiResponseStatus.VIET_JET_API_ERROR.getStatusMessage());
            return ApiResponseStatus.VIET_JET_API_ERROR;
        }
        String ticketResponse = ApiVietJet.callApiPassenger(jwtResponse, request);
        if (StringUtils.isEmpty(ticketResponse)) {
            LOGGER.info(ApiResponseStatus.INVALID_TICKET.getStatusMessage());
            return ApiResponseStatus.INVALID_TICKET;
        }
        return ApiResponseStatus.SUCCESS;
    }

    public static ApiResponseStatus validateApiTickKetScan(TicketScanRequest request, String authUserName, String authPassword){
        String jwtResponse = ApiHttpClient.getToken(
                ApiUrls.AUTHENTICATION_URL,
                authUserName,
                authPassword
        );
        if (StringUtils.isEmpty(jwtResponse)) {
            LOGGER.info(ApiResponseStatus.VIET_JET_API_ERROR.getStatusMessage());
            return ApiResponseStatus.VIET_JET_API_ERROR;
        }
        String ticketResponse = ApiVietJet.callApiScanPassenger(jwtResponse, request);
        if (StringUtils.isEmpty(ticketResponse)) {
            LOGGER.info(ApiResponseStatus.INVALID_TICKET.getStatusMessage());
            return ApiResponseStatus.INVALID_TICKET;
        }
        return ApiResponseStatus.SUCCESS;
    }
}
