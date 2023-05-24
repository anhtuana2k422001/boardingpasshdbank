package vn.com.hdbank.boardingpasshdbank.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.model.response.ConfirmCustomerVietJet;
import vn.com.hdbank.boardingpasshdbank.model.response.CustomerPrizeStatus;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.CustomerPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.InfoPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketConfirmRequest;

public interface CustomerService {
    ResponseEntity<ResponseInfo<ConfirmCustomerVietJet>>
    confirmCustomerVietJet(TicketConfirmRequest request, BindingResult bindingResult);
    ResponseEntity<ResponseInfo<CustomerPrizeStatus>>
    checkCustomerPrize(CustomerPrizeRequest request, BindingResult bindingResult);
    ResponseEntity<ResponseInfo<String>>
    updateCustomerPrize(InfoPrizeRequest request, BindingResult bindingResult);
}
