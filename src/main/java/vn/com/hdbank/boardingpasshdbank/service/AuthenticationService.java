package vn.com.hdbank.boardingpasshdbank.service;

import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.model.request.AuthenticationRequest;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.security.TokenResponse;


public interface AuthenticationService {
    ResponseInfo<TokenResponse> authenticate(AuthenticationRequest request,
                                             BindingResult bindingResult);
}
