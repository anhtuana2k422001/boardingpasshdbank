package vn.com.hdbank.boardingpasshdbank.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.ApiUrls;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.model.entity.Prize;
import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietjet;
import vn.com.hdbank.boardingpasshdbank.model.response.ConfirmCustomerVietjet;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.CustomerPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketConfirmRequest;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;
import vn.com.hdbank.boardingpasshdbank.repository.PrizeRepository;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietjetRepository;
import vn.com.hdbank.boardingpasshdbank.service.BaseService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerServiceImpl extends BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Autowired
    private PrizeRepository prizeRepository;

    public ConfirmCustomerVietjet confirmCustomerVietjet(TicketConfirmRequest ticketConfirmRequest, int customerId) {
        if (!ticketVietjetRepository.checkExistsByFlightCode(ticketConfirmRequest.getFlightCode())) {
            LOGGER.error(ApiResponseStatus.INVALID_TICKET.getStatusMessage());
            throw new CustomException(ApiResponseStatus.INVALID_TICKET);
        }

        if (Boolean.FALSE.equals(ticketConfirmRequest.getIsCustomerVietjet())) {
            LOGGER.error(ApiResponseStatus.CUSTOMER_NOT_VIETJET.getStatusMessage());
            throw new CustomException(ApiResponseStatus.CUSTOMER_NOT_VIETJET);
        }
        //ticket set customerId reference to table Customer
        ticketVietjetRepository.updateCustomerIdByFlightCode(customerId, ticketConfirmRequest.getFlightCode());
        //update customertype
        customerRepository.updateCustomerTypeById("VJ", customerId);

        //sinh mã dự thưởng và lưu vào db cho customer
        if (!prizeRepository.checkExistsPrizeCodeForVietjet(customerId)) {
            String prizeCodeGenerate = prizeRepository.generatePrizeCode();
            Prize savePrize = new Prize();
            savePrize.setCustomerId(customerId);
            savePrize.setPrizeCode(prizeCodeGenerate);
            prizeRepository.save(savePrize);
        }

        Customer customerInfo = customerRepository.findById(customerId);
        List<Prize> prizeInfo = prizeRepository.findByCustomerId(customerId);

        return new ConfirmCustomerVietjet(customerInfo, prizeInfo, ApiUrls.LINK_WEB_VIEW_PRIZES);
    }


    public ResponseInfo<Object> checkCustomerPrize(int customerId) {
        Customer customer = customerRepository.findById(customerId);

        if (LocalDateTime.now().compareTo(Constant.VJ_ESKYONE_END_DATE) > 0) {
            throw new CustomException(ApiResponseStatus.PROGRAM_ENDED);
        }

        if (customer.getCreatedAt().compareTo(Constant.VJ_ESKYONE_START_DATE) < 0 || customer.getCreatedAt().compareTo(Constant.VJ_ESKYONE_END_DATE) > 0) {
            throw new CustomException(ApiResponseStatus.NOT_ENOUGH_CONDITION_FOR_PRIZE);
        }

        Prize prizeInfo = prizeRepository.findByCustomerId(customerId).get(0);
        if (Boolean.TRUE.equals(prizeInfo.isUsed())) {
            throw new CustomException(ApiResponseStatus.CUSTOMER_JOINED_PRIZE_DRAW);
        }

        if (Boolean.FALSE.equals(prizeRepository.checkExistsPrizeCodeForVietjet(customerId))) {
            throw new CustomException(ApiResponseStatus.NO_PRIZE_CODE);
        }


        ResponseInfo<Object> response = null;
        if (Boolean.TRUE.equals(prizeInfo.isUsed()) && prizeInfo.getPrizeAmount().compareTo(new BigDecimal(0)) > 0) {
            response = ResponseInfo.<Object>builder()
                    .code(ApiResponseStatus.CUSTOMER_ROULETTE_SUCCESS.getStatusCode())
                    .message(ApiResponseStatus.CUSTOMER_ROULETTE_SUCCESS.getStatusMessage())
                    .data(prizeInfo.getPrizeAmount())
                    .build();
        } else {
            response = ResponseInfo.<Object>builder()
                    .code(ApiResponseStatus.SUCCESS.getStatusCode())
                    .message(ApiResponseStatus.SUCCESS.getStatusMessage())
                    .data("Khách hàng được phép tham gia quay thưởng")
                    .build();
        }

        return response;

    }


    // Update results prize for customer
    public boolean updateCustomerPrize (CustomerPrizeRequest request, int customerId){
        return prizeRepository.updateResultPrize(request, customerId);
    }
}
