package vn.com.hdbank.boardingpasshdbank.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.ResponseController;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.request.AuthenticationRequest;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.security.TokenResponse;
import vn.com.hdbank.boardingpasshdbank.service.AuthenticationService;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;
import vn.com.hdbank.boardingpasshdbank.utils.MdcUtils;


@RestController
@Slf4j
@RequestMapping(path = "/api")
public class SecurityController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/get-token")
    public ResponseEntity<ResponseInfo<TokenResponse>> authenticate(
            @Valid @RequestBody AuthenticationRequest request, BindingResult bindingResult) {
        try {
            MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
            LOGGER.info(Constant.REQUEST, JsonUtils.toJsonString(request)); /* Log request */
            var response = authenticationService.authenticate(request, bindingResult);
            LOGGER.info(Constant.RESPONSE, JsonUtils.toJsonString(response));   /* Log response */
            return ResponseController.responseEntity(response);
        } catch (CustomException e) {
            LOGGER.info(Constant.FORMAT_LOG, e.getStatusCode(), e.getStatusMessage());
            return ResponseController.responseEntity(e.getApiResponseStatus(), request.getRequestId());
        }
    }
}
