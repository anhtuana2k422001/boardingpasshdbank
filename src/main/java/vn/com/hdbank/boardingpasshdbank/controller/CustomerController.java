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
import vn.com.hdbank.boardingpasshdbank.common.ResponseEntityHelper;
import vn.com.hdbank.boardingpasshdbank.model.response.ConfirmCustomerVietjet;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.CustomerPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketConfirmRequest;
import vn.com.hdbank.boardingpasshdbank.service.impl.CustomerServiceImpl;
import vn.com.hdbank.boardingpasshdbank.utils.MdcUtils;
import vn.com.hdbank.boardingpasshdbank.utils.ValidationUtils;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/customer")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    CustomerServiceImpl customerService;

    /* API 2: confirm customer VietJet */
    @PostMapping("/confirm-customer-vietjet/{customerId}")
    public ResponseEntity<ResponseInfo<ConfirmCustomerVietjet>> confirmCustomerVietJet(@Valid @RequestBody TicketConfirmRequest request,
                                                                                       @PathVariable int customerId, BindingResult bindingResult){
        ValidationUtils.handleValidationErrors(bindingResult); /*validate*/
        MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
        String requestLog = JsonUtils.toJsonString(request);
        LOGGER.info(Constant.REQUEST, requestLog); /* Log request */
        ConfirmCustomerVietjet confirmCustomerVietjet = customerService.confirmCustomerVietjet(request,customerId);
        ResponseEntity<ResponseInfo<ConfirmCustomerVietjet>> responseEntity;
        responseEntity = ResponseEntityHelper.successResponseEntity(confirmCustomerVietjet);
        String responseLog = JsonUtils.toJsonString(responseEntity);
        LOGGER.info(Constant.RESPONSE, responseLog);
        MDC.clear();
        return responseEntity;
    }

    /* API 3 : Check customer prize status  */
    @PostMapping("/check-customer-prize/{customerId}")
    public ResponseEntity<ResponseInfo<Object>> checkCustomerPrize(@PathVariable int customerId){
        // TODO: MAKE AGAIN API - 3
        ResponseInfo<Object> response =  customerService.checkCustomerPrize(customerId);
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);
        String requestLog = JsonUtils.toJsonString(customerId);
        LOGGER.info(Constant.REQUEST, requestLog);
        String responseLog = JsonUtils.toJsonString(response);
        LOGGER.info(Constant.RESPONSE, responseLog);
        MDC.clear();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /* API 4 : Update customer prize  */
    @PostMapping("/update-customer-prize/{customerId}")
    public ResponseEntity<ResponseInfo<CustomerPrizeRequest>> updateCustomerPrize(@RequestBody CustomerPrizeRequest request,
                                                                                @PathVariable int customerId){
        MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
        String requestLog = JsonUtils.toJsonString(request);
        LOGGER.info(Constant.REQUEST, requestLog); /* Log request */
        boolean customerPrizeRequest =  customerService.updateCustomerPrize(request, customerId);
        ResponseEntity<ResponseInfo<CustomerPrizeRequest>> responseEntity;
        if(customerPrizeRequest){
            responseEntity = ResponseEntityHelper.successResponseEntity();
        }else{
            responseEntity = ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.UPDATE_PRIZE_ERROR);
        }
        String responseLog = JsonUtils.toJsonString(responseEntity);
        LOGGER.info(Constant.RESPONSE, responseLog);
        MDC.clear();
        return responseEntity;
    }

}
