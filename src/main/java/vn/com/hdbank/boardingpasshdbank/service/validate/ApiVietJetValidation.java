package vn.com.hdbank.boardingpasshdbank.service.validate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.ApiUrls;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketScanRequest;
import vn.com.hdbank.boardingpasshdbank.service.utils.ApiVietJet;
import vn.com.hdbank.boardingpasshdbank.utils.ApiHttpClient;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiVietJetValidation {
    public static ApiResponseStatus validateApiTickKet(TicketRequest request, String authUserName, String authPassword){
        String jwtResponse = ApiHttpClient.getToken(
                ApiUrls.AUTHENTICATION_URL,
                authUserName,
                authPassword
        );

        if (StringUtils.isEmpty(jwtResponse)) {
            return ApiResponseStatus.VIET_JET_API_ERROR;
        }

        String ticketResponse = ApiVietJet.callApiPassenger(jwtResponse, request);
        if (StringUtils.isEmpty(ticketResponse)) {
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
            return ApiResponseStatus.VIET_JET_API_ERROR;
        }

        String ticketResponse = ApiVietJet.callApiScanPassenger(jwtResponse, request);
        if (StringUtils.isEmpty(ticketResponse)) {
            return ApiResponseStatus.INVALID_TICKET;
        }

        return ApiResponseStatus.SUCCESS;
    }
}
