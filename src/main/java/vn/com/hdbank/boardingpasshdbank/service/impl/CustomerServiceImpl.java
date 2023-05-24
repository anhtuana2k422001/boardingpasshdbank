package vn.com.hdbank.boardingpasshdbank.service.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.ResponseService;
import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.model.entity.Prize;
import vn.com.hdbank.boardingpasshdbank.model.response.ConfirmCustomerVietJet;
import vn.com.hdbank.boardingpasshdbank.model.response.CustomerPrizeStatus;
import vn.com.hdbank.boardingpasshdbank.model.response.PrizeResult;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.CustomerPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.InfoPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketConfirmRequest;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;
import vn.com.hdbank.boardingpasshdbank.repository.PrizeRepository;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietJetRepository;
import vn.com.hdbank.boardingpasshdbank.service.CustomerService;
import vn.com.hdbank.boardingpasshdbank.service.validate.DatabaseValidation;
import vn.com.hdbank.boardingpasshdbank.utils.ValidationUtils;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PrizeRepository prizeRepository;
    private final TicketVietJetRepository ticketVietjetRepository;

    @Override
    public ResponseEntity<ResponseInfo<ConfirmCustomerVietJet>> confirmCustomerVietJet(TicketConfirmRequest request,
                                                                                       BindingResult bindingResult) {
        String requestId = request.getRequestId();
        int customerId = request.getCustomerId();
        String reservationCode = request.getReservationCode();
        /*------------------- Validate ticket request -------------------*/
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            return ResponseService.validateResponseEntity(errors, request.getRequestId());
        /*------------------- Validate database -------------------*/
        ApiResponseStatus response =  DatabaseValidation.validateConfirmCustomer(request,
                customerRepository, ticketVietjetRepository);
        if (!StringUtils.equals(Constant.SUCCESS_CODE, response.getStatusCode())) {
            return ResponseService.errorResponseEntity(response, requestId);
        }
        /* Ticket set customerId reference to table Customer */
        ticketVietjetRepository.updateCustomerIdByFlightCode(customerId, reservationCode);
        /* Update Customer Type */
        customerRepository.updateCustomerTypeById("VJ", customerId);
        /* generate bonus code and save it in db for customer */
        if (!prizeRepository.checkExistsPrizeCodeForVietJet(customerId)) {
            String prizeCodeGenerate = prizeRepository.generatePrizeCode();
            Prize savePrize = new Prize();
            savePrize.setCustomerId(customerId);
            savePrize.setPrizeCode(prizeCodeGenerate);
            prizeRepository.save(savePrize);
        }
        List<Prize> prizeInfo = prizeRepository.findByCustomerId(customerId);
        Customer customerInfo = customerRepository.findById(customerId);
        ConfirmCustomerVietJet confirmCustomerVietjet = new ConfirmCustomerVietJet(customerInfo, prizeInfo, Constant.LINK_WEB_PRIZES);
        return ResponseService.successResponseEntity(
                ApiResponseStatus.SUCCESS, confirmCustomerVietjet, requestId);
    }

    @Override
    public ResponseEntity<ResponseInfo<CustomerPrizeStatus>> checkCustomerPrize(CustomerPrizeRequest request,
                                                                                BindingResult bindingResult) {
        int customerId = request.getCustomerId();
        String requestId = request.getRequestId();
        /*------------------- Validate ticket request -------------------*/
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            return ResponseService.validateResponseEntity(errors, requestId);
        /*------------------- Validate database -------------------*/
        ApiResponseStatus response =  DatabaseValidation.validateCheckPrize(request,
                customerRepository, prizeRepository);

        if (!StringUtils.equals(Constant.SUCCESS_CODE, response.getStatusCode())) {
            return ResponseService.errorResponseEntity(response, requestId);
        }

        Prize prizeInfo = prizeRepository.findByCustomerId(customerId).get(0);
        if(Boolean.TRUE.equals(prizeInfo.isUsed())){
            return ResponseService.successResponseEntity(ApiResponseStatus.SUCCESS,
                    new CustomerPrizeStatus(
                    Boolean.TRUE,
                    Constant.CUSTOMER_PRIZE_SUCCESS,
                    new PrizeResult(
                            Constant.VIET_JET_LUCKY_CONTENT,
                            Constant.BANK_ACCOUNT,
                            Constant.BALANCE_AFTER_TRANSACTION,
                            prizeInfo.getPrizeAmount()),
                    null
            ), requestId);
        }

        return ResponseService.successResponseEntity(ApiResponseStatus.SUCCESS,
                new CustomerPrizeStatus(
                Boolean.FALSE,
                Constant.PRIZE_SUCCESS_NOT_DIALED,
                null,
                Constant.LINK_WEB_PRIZES
        ), requestId);

    }

    /* Update results prize for customer */
    @Override
    public ResponseEntity<ResponseInfo<String>> updateCustomerPrize(InfoPrizeRequest request,
                                                                    BindingResult bindingResult){
        String requestId = request.getRequestId();

        /*------------------- Validate ticket request -------------------*/
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            return ResponseService.validateResponseEntity(errors, requestId);

        /*------------------- Validate database -------------------*/
        ApiResponseStatus response =  DatabaseValidation.validateUpdatePrize(request,
                customerRepository, prizeRepository);
        if (!StringUtils.equals(Constant.SUCCESS_CODE, response.getStatusCode())) {
            return ResponseService.errorResponseEntity(response, requestId);
        }

        return ResponseService.successResponseEntity(requestId);
    }
}
