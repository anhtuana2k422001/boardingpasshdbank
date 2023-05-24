package vn.com.hdbank.boardingpasshdbank.service.validate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietJet;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.*;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;
import vn.com.hdbank.boardingpasshdbank.repository.PrizeRepository;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietJetRepository;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DatabaseValidation {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseValidation.class);

    public static ApiResponseStatus validateTicket(String reservationCode,
                                                   TicketVietJetRepository ticketVietjetRepository) {
        List<TicketVietJet> ticketVietjetList = ticketVietjetRepository.findCustomerIdNotNull(reservationCode);
        if (!ticketVietjetList.isEmpty()) {
            LOGGER.info(ApiResponseStatus.VIET_JET_EXISTED_AND_ASSIGNED.getStatusMessage());
            return ApiResponseStatus.VIET_JET_EXISTED_AND_ASSIGNED;
        }

        return ApiResponseStatus.SUCCESS;
    }

    public static ApiResponseStatus validateConfirmCustomer(TicketConfirmRequest request,
                                                            CustomerRepository customerRepository,
                                                            TicketVietJetRepository ticketVietjetRepository) {
        int customerId = request.getCustomerId();
        String reservationCode = request.getReservationCode();

        if (Boolean.FALSE.equals(request.getIsCustomerVietJet())) {
            LOGGER.info(ApiResponseStatus.CUSTOMER_NOT_VIET_JET.getStatusMessage());
            return ApiResponseStatus.CUSTOMER_NOT_VIET_JET;
        }

        if (!ticketVietjetRepository.checkExistsByFlightCode(reservationCode)) {
            LOGGER.info(ApiResponseStatus.INVALID_TICKET.getStatusMessage());
            return ApiResponseStatus.INVALID_TICKET;
        }

        Customer customerInfo = customerRepository.findById(customerId);
        if (customerInfo == null) {
            LOGGER.info(ApiResponseStatus.NOT_FOUND_CUSTOMER.getStatusMessage());
            return ApiResponseStatus.NOT_FOUND_CUSTOMER;
        }

        boolean usedTicket = customerRepository.usedTicket(request);
        if (!usedTicket) {
            LOGGER.info(ApiResponseStatus.USED_TICKET_ERROR.getStatusMessage());
            return ApiResponseStatus.USED_TICKET_ERROR;
        }

        return ApiResponseStatus.SUCCESS;
    }

    public static ApiResponseStatus validateCheckPrize(CustomerPrizeRequest request,
                                                       CustomerRepository customerRepository,
                                                       PrizeRepository prizeRepository) {
        int customerId = request.getCustomerId();
        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            LOGGER.info(ApiResponseStatus.NOT_FOUND_CUSTOMER.getStatusMessage());
            return ApiResponseStatus.NOT_FOUND_CUSTOMER;
        }

        if (LocalDateTime.now().isAfter(Constant.VJ_E_SKY_ONE_END_DATE)) {
            return ApiResponseStatus.PROGRAM_ENDED;
        }

        if (customer.getCreatedAt().isBefore(Constant.VJ_E_SKY_ONE_START_DATE)
                || customer.getCreatedAt().isAfter(Constant.VJ_E_SKY_ONE_END_DATE)) {
            LOGGER.info(ApiResponseStatus.NOT_ENOUGH_CONDITION_FOR_PRIZE.getStatusMessage());
            return ApiResponseStatus.NOT_ENOUGH_CONDITION_FOR_PRIZE;
        }

        if (Boolean.FALSE.equals(prizeRepository.checkExistsPrizeCodeForVietJet(customerId))) {
            LOGGER.info(ApiResponseStatus.NO_PRIZE_CODE.getStatusMessage());
            return ApiResponseStatus.NO_PRIZE_CODE;
        }

        return ApiResponseStatus.SUCCESS;
    }

    public static ApiResponseStatus validateUpdatePrize(InfoPrizeRequest request,
                                                        CustomerRepository customerRepository,
                                                        PrizeRepository prizeRepository) {
        int customerId = request.getCustomerId();
        Customer customer = customerRepository.findById(customerId);

        if (customer == null) {
            LOGGER.info(ApiResponseStatus.NOT_FOUND_CUSTOMER.getStatusMessage());
            return ApiResponseStatus.NOT_FOUND_CUSTOMER;
        }

        boolean checkResult = prizeRepository.checkResultPrize(request);
        if (!checkResult) {
            LOGGER.info(ApiResponseStatus.PRIZE_CODE_ERROR.getStatusMessage());
            return ApiResponseStatus.PRIZE_CODE_ERROR;
        }

        boolean checkUsed = prizeRepository.checkUsedPrize(request);
        if (checkUsed) {
            LOGGER.info(ApiResponseStatus.USED_PRIZE_CODE.getStatusMessage());
            return ApiResponseStatus.USED_PRIZE_CODE;
        }

        boolean updated = prizeRepository.updateResultPrize(request);
        if (!updated)
            return ApiResponseStatus.UPDATE_PRIZE_ERROR;

        return ApiResponseStatus.SUCCESS;
    }

}
