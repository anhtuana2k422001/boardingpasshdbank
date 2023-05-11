package vn.com.hdbank.boardingpasshdbank.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketConfirmRequest;
import vn.com.hdbank.boardingpasshdbank.service.impl.CustomerServiceImpl;
import vn.com.hdbank.boardingpasshdbank.service.impl.TicketVietJetServiceImpl;
import vn.com.hdbank.boardingpasshdbank.utils.CommonUtils;

@RestController
@RequestMapping(path = "/api/customer")
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    TicketVietJetServiceImpl ticketVietJetService;

    //API 2
    @PostMapping("/confirm-customer-vietjet/{customerId}")
    public ResponseEntity<ResponseInfo<Customer>> confirmCustomerVietjet(@Valid @RequestBody TicketConfirmRequest ticketConfirmRequest, @RequestParam int customerId, BindingResult bindingResult){

        CommonUtils.handleValidationErrors(bindingResult); //validate

        Customer customerInformation = customerService.confirmCustomerVietjet(ticketConfirmRequest,customerId);
        ResponseInfo<Customer> response = ResponseInfo.<Customer>builder()
                .data(customerInformation)
                .code(ApiResponseStatus.SUCCESS.getStatusCode())
                .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
