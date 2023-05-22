package vn.com.hdbank.boardingpasshdbank.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.ResponseEntityHelper;
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
import vn.com.hdbank.boardingpasshdbank.service.BaseService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerServiceImpl extends BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PrizeRepository prizeRepository;

    public ResponseEntity<ResponseInfo<ConfirmCustomerVietJet>> confirmCustomerVietJet(TicketConfirmRequest request) {
        String requestId = request.getRequestId();
        int customerId = request.getCustomerId();
        String flightCode = request.getFlightCode();
        if (!ticketVietjetRepository.checkExistsByFlightCode(flightCode)) {
            LOGGER.info(ApiResponseStatus.INVALID_TICKET.getStatusMessage());
            return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.INVALID_TICKET, requestId);
        }

        if (Boolean.FALSE.equals(request.getIsCustomerVietJet())) {
            LOGGER.info(ApiResponseStatus.CUSTOMER_NOT_VIET_JET.getStatusMessage());
            return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.CUSTOMER_NOT_VIET_JET, requestId);
        }

        Customer customerInfo = customerRepository.findById(customerId);
        if (customerInfo == null) {
            return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.NOT_FOUND_CUSTOMER, requestId);
        }

        /* Ticket set customerId reference to table Customer*/
        ticketVietjetRepository.updateCustomerIdByFlightCode(customerId, flightCode);
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
        ConfirmCustomerVietJet confirmCustomerVietjet = new ConfirmCustomerVietJet(customerInfo, prizeInfo, Constant.LINK_WEB_PRIZES);
        return ResponseEntityHelper.successResponseEntity(confirmCustomerVietjet, requestId);
    }


    public ResponseEntity<ResponseInfo<CustomerPrizeStatus>> checkCustomerPrize(CustomerPrizeRequest request) {
        int customerId = request.getCustomerId();
        String requestId = request.getRequestId();

        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.NOT_FOUND_CUSTOMER, requestId);
        }

        if (LocalDateTime.now().isAfter(Constant.VJ_E_SKY_ONE_END_DATE)) {
            return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.PROGRAM_ENDED, requestId);
        }

        if (customer.getCreatedAt().isBefore(Constant.VJ_E_SKY_ONE_START_DATE)
                || customer.getCreatedAt().isAfter(Constant.VJ_E_SKY_ONE_END_DATE)) {
            return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.NOT_ENOUGH_CONDITION_FOR_PRIZE, requestId);
        }

        if (Boolean.FALSE.equals(prizeRepository.checkExistsPrizeCodeForVietJet(customerId))) {
            return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.NO_PRIZE_CODE, requestId);
        }

        Prize prizeInfo = prizeRepository.findByCustomerId(customerId).get(0);
        if(Boolean.TRUE.equals(prizeInfo.isUsed())){
            return ResponseEntityHelper.successResponseEntity(new CustomerPrizeStatus(
                    Boolean.TRUE,
                    Constant.CUSTOMER_PRIZE_SUCCESS,
                    new PrizeResult(
                            Constant.VIET_JET_LUCKY_CONTENT,
                            Constant.BANK_ACCOUNT,
                            Constant.BALANCE_AFTER_TRANSACTION,
                            prizeInfo.getPrizeAmount()),
                    null
            ), requestId);
        }else{
            return ResponseEntityHelper.successResponseEntity(new CustomerPrizeStatus(
                    Boolean.FALSE,
                    Constant.PRIZE_SUCCESS_NOT_DIALED,
                    null,
                    Constant.LINK_WEB_PRIZES
            ), requestId);
        }
    }

    /* Update results prize for customer */
    public ResponseEntity<ResponseInfo<String>> updateCustomerPrize(InfoPrizeRequest request){
        String requestId = request.getRequestId();
        int customerId = request.getCustomerId();
        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.NOT_FOUND_CUSTOMER, requestId);
        }
        boolean updated =  prizeRepository.updateResultPrize(request, customerId);
        if(!updated)
            return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.UPDATE_PRIZE_ERROR, requestId);
        return ResponseEntityHelper.successResponseEntity(requestId);
    }
}
