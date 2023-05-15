package vn.com.hdbank.boardingpasshdbank.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.model.response.ConfirmCustomerVietjet;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketConfirmRequest;
import vn.com.hdbank.boardingpasshdbank.service.impl.CustomerServiceImpl;
import vn.com.hdbank.boardingpasshdbank.service.impl.TicketVietJetServiceImpl;
import vn.com.hdbank.boardingpasshdbank.utils.CommonUtils;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;
import vn.com.hdbank.boardingpasshdbank.utils.Utils;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/customer")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    TicketVietJetServiceImpl ticketVietJetService;

    /* API 2: confirm customer VietJet */
    @PostMapping("/confirm-customer-vietjet/{customerId}")
    public ResponseEntity<ResponseInfo<ConfirmCustomerVietjet>> confirmCustomerVietJet(@Valid @RequestBody TicketConfirmRequest request,
                                                                                       @PathVariable int customerId, BindingResult bindingResult){
        CommonUtils.handleValidationErrors(bindingResult); /*validate*/
        if(Utils.isEmpty(request.getRequestId())){
            String requestId = UUID.randomUUID().toString();
            MDC.put("requestId", requestId);
        }
        MDC.put("requestId", request.getRequestId());
        String requestLog = JsonUtils.toJsonString(request);
        LOGGER.info(Constant.REQUEST, requestLog);
        ConfirmCustomerVietjet confirmCustomerVietjet = customerService.confirmCustomerVietjet(request,customerId);
        ResponseInfo<ConfirmCustomerVietjet> response = ResponseInfo.<ConfirmCustomerVietjet>builder()
                .data(confirmCustomerVietjet)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        String responseLog = JsonUtils.toJsonString(response);
        LOGGER.info(Constant.RESPONSE, responseLog);
        MDC.clear();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
