package vn.com.hdbank.boardingpasshdbank.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.ResponseService;
import vn.com.hdbank.boardingpasshdbank.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.model.request.AuthenticationRequest;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;
import vn.com.hdbank.boardingpasshdbank.security.JwtUtilities;
import vn.com.hdbank.boardingpasshdbank.security.TokenResponse;
import vn.com.hdbank.boardingpasshdbank.service.AuthenticationService;
import vn.com.hdbank.boardingpasshdbank.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager ;
    private final JwtUtilities jwtUtilities;
    private final CustomerRepository customerRepository;

    @Override
    public ResponseInfo<TokenResponse> authenticate(AuthenticationRequest request, BindingResult bindingResult) {
        String requestId = request.getRequestId();
        try {
            /*------------------- Validate ticket request -------------------*/
            Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
            if (errors.size() > 0)
                return ResponseService.validateResponse(errors, requestId);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

             Customer customer = customerRepository.findByPhoneNumber(request.getPhoneNumber());
            if (customer == null)
                return ResponseService.errorResponse(ApiResponseStatus.NOT_FOUND_CUSTOMER, requestId);

            List<String> rolesNames = new ArrayList<>();
            rolesNames.add(customer.getRoleId());

            TokenResponse tokenResponse = jwtUtilities.generateToken(request.getPhoneNumber(), rolesNames);
            return ResponseService.successResponse(ApiResponseStatus.SUCCESS,
                    tokenResponse, requestId);

        } catch (AuthenticationException e) {
            return ResponseService.errorResponse(ApiResponseStatus.UNAUTHORIZED_ACCESS, requestId);
        }
    }


}
