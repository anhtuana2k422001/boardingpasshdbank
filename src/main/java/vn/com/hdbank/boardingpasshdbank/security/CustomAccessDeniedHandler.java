package vn.com.hdbank.boardingpasshdbank.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseError;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        ResponseError errorResponse = new ResponseError();
        errorResponse.setCode(ApiResponseStatus.FORBIDDEN_ACCESS.getStatusCode());
        errorResponse.setMessage(ApiResponseStatus.FORBIDDEN_ACCESS.getStatusMessage());

        response.setCharacterEncoding(Constant.UTF_8);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JsonUtils.toJsonString(errorResponse));
    }
}
