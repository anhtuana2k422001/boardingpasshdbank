package vn.com.hdbank.boardingpasshdbank.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.ResponseController;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.response.ConfirmCustomerVietJet;
import vn.com.hdbank.boardingpasshdbank.model.response.CustomerPrizeStatus;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.CustomerPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.InfoPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketConfirmRequest;
import vn.com.hdbank.boardingpasshdbank.service.CustomerService;
import vn.com.hdbank.boardingpasshdbank.utils.MdcUtils;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;


@RestController
@RequestMapping(path = "/api/customer")
@Slf4j
public class CustomerController {
    @Autowired
    CustomerService customerService;

    /* API 2: confirm customer DatabaseValidation */
    @PostMapping("/confirm-customer-vietjet")
    public org.springframework.http.ResponseEntity<ResponseInfo<ConfirmCustomerVietJet>>
    confirmCustomerVietJet(@Valid @RequestBody TicketConfirmRequest request, BindingResult bindingResult){
        try{
            MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
            LOGGER.info(Constant.REQUEST, JsonUtils.toJsonString(request)); /* Log request */
            var response = customerService.confirmCustomerVietJet(request, bindingResult);
            LOGGER.info(Constant.RESPONSE, JsonUtils.toJsonString(response));
            return ResponseController.responseEntity(response);
        }catch (CustomException e){
            LOGGER.info(Constant.FORMAT_LOG, e.getStatusCode(), e.getStatusMessage());
            return ResponseController.responseEntity(e.getApiResponseStatus(), request.getRequestId());
        }
    }

    /* API 3 : Check customer prize status  */
    @PostMapping("/check-customer-prize")
    public org.springframework.http.ResponseEntity<ResponseInfo<CustomerPrizeStatus>>
    checkCustomerPrize(@Valid @RequestBody CustomerPrizeRequest request, BindingResult bindingResult){
        try{
            MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
            LOGGER.info(Constant.REQUEST, JsonUtils.toJsonString(request)); /* Log request */
            var response =  customerService.checkCustomerPrize(request, bindingResult);
            LOGGER.info(Constant.RESPONSE, JsonUtils.toJsonString(response));
            return ResponseController.responseEntity(response);
        }catch (CustomException e){
            LOGGER.info(Constant.FORMAT_LOG, e.getStatusCode(), e.getStatusMessage());
            return ResponseController.responseEntity(e.getApiResponseStatus(), request.getRequestId());
        }
    }

    /* API 4 : Update customer prize  */
    @PostMapping("/update-customer-prize")
    public org.springframework.http.ResponseEntity<ResponseInfo<String>>
    updateCustomerPrize(@Valid @RequestBody InfoPrizeRequest request, BindingResult bindingResult) {
        try{
            MdcUtils.setRequestId(request.getRequestId()); /* Add requestId to log */
            LOGGER.info(Constant.REQUEST, JsonUtils.toJsonString(request)); /* Log request */
            var response = customerService.updateCustomerPrize(request, bindingResult);
            LOGGER.info(Constant.RESPONSE, JsonUtils.toJsonString(response));
            return ResponseController.responseEntity(response);
        }catch (CustomException e){
            LOGGER.info(Constant.FORMAT_LOG, e.getStatusCode(), e.getStatusMessage());
            return ResponseController.responseEntity(e.getApiResponseStatus(), request.getRequestId());
        }
    }

}
