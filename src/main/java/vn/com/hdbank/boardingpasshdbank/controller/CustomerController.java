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
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.ResponseEntityHelper;
import vn.com.hdbank.boardingpasshdbank.model.response.ConfirmCustomerVietJet;
import vn.com.hdbank.boardingpasshdbank.model.response.CustomerPrizeStatus;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.response.ResultPrize;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.CustomerPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.InfoPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketConfirmRequest;
import vn.com.hdbank.boardingpasshdbank.service.impl.CustomerServiceImpl;
import vn.com.hdbank.boardingpasshdbank.utils.MdcUtils;
import vn.com.hdbank.boardingpasshdbank.utils.ValidationUtils;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/customer")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    CustomerServiceImpl customerService;

    /* API 2: confirm customer VietJet */
    @PostMapping("/confirm-customer-vietjet")
    public ResponseEntity<ResponseInfo<ConfirmCustomerVietJet>> confirmCustomerVietJet(@Valid @RequestBody TicketConfirmRequest request,
                                                                                       BindingResult bindingResult){
        ResponseEntity<ResponseInfo<ConfirmCustomerVietJet>> responseEntity;
        //ValidationUtils.handleValidationErrors(bindingResult); /*validate*/
        MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
        String requestLog = JsonUtils.toJsonString(request);
        LOGGER.info(Constant.REQUEST, requestLog); /* Log request */
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            responseEntity =  ResponseEntityHelper.validateResponseEntity(errors, request.getRequestId());
        else
            responseEntity = customerService.confirmCustomerVietjet(request);
        String responseLog = JsonUtils.toJsonString(responseEntity);
        LOGGER.info(Constant.RESPONSE, responseLog);
        MDC.clear();
        return responseEntity;
    }

    /* API 3 : Check customer prize status  */
    @PostMapping("/check-customer-prize")
    public ResponseEntity<ResponseInfo<CustomerPrizeStatus>> checkCustomerPrize(@Valid @RequestBody CustomerPrizeRequest request,
                                                                                BindingResult bindingResult){
        ResponseEntity<ResponseInfo<CustomerPrizeStatus>> responseEntity;
        MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
        String requestLog = JsonUtils.toJsonString(request);
        LOGGER.info(Constant.REQUEST, requestLog); /* Log request */
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            responseEntity =  ResponseEntityHelper.validateResponseEntity(errors, request.getRequestId());
        else
            responseEntity =  customerService.checkCustomerPrize(request);
        String responseLog = JsonUtils.toJsonString(responseEntity);
        LOGGER.info(Constant.RESPONSE, responseLog);
        MDC.clear();
        return responseEntity;
    }

    /* API 4 : Update customer prize  */
    @PostMapping("/update-customer-prize")
    public ResponseEntity<ResponseInfo<ResultPrize>> updateCustomerPrize(@Valid @RequestBody InfoPrizeRequest request,
                                                                         BindingResult bindingResult){
        ResponseEntity<ResponseInfo<ResultPrize>> responseEntity;
        MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
        String requestLog = JsonUtils.toJsonString(request);
        LOGGER.info(Constant.REQUEST, requestLog); /* Log request */
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            responseEntity =  ResponseEntityHelper.validateResponseEntity(errors, request.getRequestId());
        else
            responseEntity = customerService.updateCustomerPrize(request);
        String responseLog = JsonUtils.toJsonString(responseEntity);
        LOGGER.info(Constant.RESPONSE, responseLog);
        MDC.clear();
        return responseEntity;
    }

}
