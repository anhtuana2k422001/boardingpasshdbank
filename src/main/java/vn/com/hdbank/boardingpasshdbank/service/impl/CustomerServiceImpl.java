package vn.com.hdbank.boardingpasshdbank.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.ApiUrls;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.ResponseEntityHelper;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.model.entity.Prize;
import vn.com.hdbank.boardingpasshdbank.model.response.ConfirmCustomerVietJet;
import vn.com.hdbank.boardingpasshdbank.model.response.CustomerPrizeStatus;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.response.ResultPrize;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.CustomerPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.InfoPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketConfirmRequest;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;
import vn.com.hdbank.boardingpasshdbank.repository.PrizeRepository;
import vn.com.hdbank.boardingpasshdbank.service.BaseService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerServiceImpl extends BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected PrizeRepository prizeRepository;

    public ResponseEntity<ResponseInfo<ConfirmCustomerVietJet>> confirmCustomerVietjet(TicketConfirmRequest request) {
        if (!ticketVietjetRepository.checkExistsByFlightCode(request.getFlightCode())) {
            LOGGER.info(ApiResponseStatus.INVALID_TICKET.getStatusMessage());
            return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.INVALID_TICKET, request.getRequestId());
        }

        if (Boolean.FALSE.equals(request.getIsCustomerVietjet())) {
            LOGGER.info(ApiResponseStatus.CUSTOMER_NOT_VIETJET.getStatusMessage());
            return ResponseEntityHelper.errorResponseEntity(ApiResponseStatus.CUSTOMER_NOT_VIETJET, request.getRequestId());
        }
        /* Ticket set customerId reference to table Customer*/
        ticketVietjetRepository.updateCustomerIdByFlightCode(request.getCustomerId(), request.getFlightCode());
        /* Update Customer Type */
        customerRepository.updateCustomerTypeById("VJ", request.getCustomerId());
        /* generate bonus code and save it in db for customer */
        if (!prizeRepository.checkExistsPrizeCodeForVietjet(request.getCustomerId())) {
            String prizeCodeGenerate = prizeRepository.generatePrizeCode();
            Prize savePrize = new Prize();
            savePrize.setCustomerId(request.getCustomerId());
            savePrize.setPrizeCode(prizeCodeGenerate);
            prizeRepository.save(savePrize);
        }
        Customer customerInfo = customerRepository.findById(request.getCustomerId());
        List<Prize> prizeInfo = prizeRepository.findByCustomerId(request.getCustomerId());
        ConfirmCustomerVietJet confirmCustomerVietjet = new ConfirmCustomerVietJet(customerInfo, prizeInfo, ApiUrls.LINK_WEB_VIEW_PRIZES);

        return ResponseEntityHelper.successResponseEntity(confirmCustomerVietjet, request.getRequestId());
    }


    public ResponseEntity<ResponseInfo<CustomerPrizeStatus>> checkCustomerPrize(CustomerPrizeRequest request) {
        // TODO: MAKE AGAIN API - 3
        int customerId = request.getCustomerId();
        Customer customer = customerRepository.findById(customerId);
        if (LocalDateTime.now().compareTo(Constant.VJ_E_SKY_ONE_END_DATE) > 0) {
            throw new CustomException(ApiResponseStatus.PROGRAM_ENDED);
        }
        if (customer.getCreatedAt().compareTo(Constant.VJ_E_SKY_ONE_START_DATE) < 0 || customer.getCreatedAt().compareTo(Constant.VJ_E_SKY_ONE_END_DATE) > 0) {
            throw new CustomException(ApiResponseStatus.NOT_ENOUGH_CONDITION_FOR_PRIZE);
        }
        Prize prizeInfo = prizeRepository.findByCustomerId(customerId).get(0);
        if (Boolean.TRUE.equals(prizeInfo.isUsed())) {
            throw new CustomException(ApiResponseStatus.CUSTOMER_JOINED_PRIZE_DRAW);
        }
        if (Boolean.FALSE.equals(prizeRepository.checkExistsPrizeCodeForVietjet(customerId))) {
            throw new CustomException(ApiResponseStatus.NO_PRIZE_CODE);
        }

        CustomerPrizeStatus customerPrizeStatus;
        if (Boolean.TRUE.equals(prizeInfo.isUsed()) && prizeInfo.getPrizeAmount().compareTo(new BigDecimal(0)) > 0) {
            customerPrizeStatus = new CustomerPrizeStatus(customer, null, prizeInfo.getPrizeAmount(), ApiUrls.LINK_WEB_VIEW_PRIZES);
            return ResponseEntityHelper.successResponseEntity(ApiResponseStatus.CUSTOMER_ROULETTE_SUCCESS,
                    customerPrizeStatus,  request.getRequestId());
        } else {
            customerPrizeStatus = new CustomerPrizeStatus(customer, null, prizeInfo.getPrizeAmount(), ApiUrls.LINK_WEB_VIEW_PRIZES);
            return ResponseEntityHelper.successResponseEntity(ApiResponseStatus.CUSTOMER_PRIZE_SUCCESS,
                    customerPrizeStatus,  request.getRequestId());
        }
    }

    /* Update results prize for customer */
    public ResponseEntity<ResponseInfo<ResultPrize>>  updateCustomerPrize(InfoPrizeRequest request){
        boolean updated =  prizeRepository.updateResultPrize(request, request.getCustomerId());
        ResultPrize resultPrize  = null;
        // TODO: MAKE AGAIN API - 4 LOGIC
        // totle

        if(updated)
            resultPrize = new ResultPrize("MAKE AGAIN API - 4", 5500.0);
        return ResponseEntityHelper.successResponseEntity(resultPrize, request.getRequestId());
    }
}
