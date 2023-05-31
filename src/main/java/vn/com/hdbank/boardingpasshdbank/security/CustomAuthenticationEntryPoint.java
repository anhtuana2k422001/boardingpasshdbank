package vn.com.hdbank.boardingpasshdbank.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseError;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint  implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ResponseError errorResponse = new ResponseError();
        errorResponse.setCode(ApiResponseStatus.UNAUTHORIZED_ACCESS.getStatusCode());
        errorResponse.setMessage(ApiResponseStatus.UNAUTHORIZED_ACCESS.getStatusMessage());

        response.setCharacterEncoding(Constant.UTF_8);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JsonUtils.toJsonString(errorResponse));
    }
}
