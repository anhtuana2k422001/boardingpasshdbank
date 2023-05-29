package vn.com.hdbank.boardingpasshdbank.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.ResponseService;
import vn.com.hdbank.boardingpasshdbank.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.entity.Prize;
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
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PrizeRepository prizeRepository;
    private final TicketVietJetRepository ticketVietjetRepository;

    @Override
    public ResponseInfo<ConfirmCustomerVietJet> confirmCustomerVietJet(TicketConfirmRequest request,
                                                                                       BindingResult bindingResult) {
        String requestId = request.getRequestId();
        int customerId = request.getCustomerId();
        String reservationCode = request.getReservationCode();

        /*------------------- Validate ticket request -------------------*/
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            return ResponseService.validateResponse(errors, request.getRequestId());

        /*------------------- Validate database -------------------*/
        ApiResponseStatus apiResponseStatus =  DatabaseValidation.validateConfirmCustomer(request,
                customerRepository, ticketVietjetRepository);

        if(!ApiResponseStatus.SUCCESS.equals(apiResponseStatus)){
            LOGGER.info(Constant.FORMAT_LOG, apiResponseStatus.getStatusCode(), apiResponseStatus.getStatusMessage());
            return ResponseService.errorResponse(apiResponseStatus, requestId);
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

        return ResponseService.successResponse(ApiResponseStatus.SUCCESS,
                confirmCustomerVietjet, requestId);
    }

    @Override
    public ResponseInfo<CustomerPrizeStatus> checkCustomerPrize(CustomerPrizeRequest request,
                                                                                BindingResult bindingResult) {
        int customerId = request.getCustomerId();
        String requestId = request.getRequestId();

        /*------------------- Validate ticket request -------------------*/
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            return ResponseService.validateResponse(errors, requestId);

        /*------------------- Validate database -------------------*/
        ApiResponseStatus apiResponseStatus =  DatabaseValidation.validateCheckPrize(request,
                customerRepository, prizeRepository);

        if (!ApiResponseStatus.SUCCESS.equals(apiResponseStatus)) {
            LOGGER.info(Constant.FORMAT_LOG, apiResponseStatus.getStatusCode(), apiResponseStatus.getStatusMessage());
            return ResponseService.errorResponse(apiResponseStatus, requestId);
        }

        Prize prizeInfo = prizeRepository.findByCustomerId(customerId).get(0);
        if(Boolean.TRUE.equals(prizeInfo.isUsed())){
            return ResponseService.successResponse(ApiResponseStatus.CUSTOMER_PRIZE_SUCCESS,
                    new CustomerPrizeStatus(new PrizeResult(
                            Constant.VIET_JET_LUCKY_CONTENT,
                            Constant.BANK_ACCOUNT,
                            Constant.BALANCE_AFTER_TRANSACTION,
                            prizeInfo.getPrizeAmount().doubleValue()),
                    null
            ), requestId);
        }

        return ResponseService.successResponse(ApiResponseStatus.PRIZE_SUCCESS_NOT_DIALED,
                new CustomerPrizeStatus(
                null,
                Constant.LINK_WEB_PRIZES
        ), requestId);

    }

    /* Update results prize for customer */
    @Override
    public ResponseInfo<String> updateCustomerPrize(InfoPrizeRequest request,
                                                                    BindingResult bindingResult){
        String requestId = request.getRequestId();

        /*------------------- Validate ticket request -------------------*/
        Map<String, String> errors = ValidationUtils.validationHandler(bindingResult);
        if (errors.size() > 0)
            return ResponseService.validateResponse(errors, requestId);

        /*------------------- Validate database -------------------*/
        ApiResponseStatus apiResponseStatus =  DatabaseValidation.validateUpdatePrize(request,
                customerRepository, prizeRepository);

        if(!ApiResponseStatus.SUCCESS.equals(apiResponseStatus)){
            LOGGER.info(Constant.FORMAT_LOG, apiResponseStatus.getStatusCode(), apiResponseStatus.getStatusMessage());
            return ResponseService.errorResponse(apiResponseStatus, requestId);
        }

        return ResponseService.successResponse(ApiResponseStatus.SUCCESS, requestId);
    }
}
