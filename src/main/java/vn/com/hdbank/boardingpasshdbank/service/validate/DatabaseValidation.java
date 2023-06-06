package vn.com.hdbank.boardingpasshdbank.service.validate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.*;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;
import vn.com.hdbank.boardingpasshdbank.repository.PrizeRepository;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietJetRepository;
import vn.com.hdbank.boardingpasshdbank.utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class DatabaseValidation {

    public static ApiResponseStatus validateTicket(String reservationCode, String flightCode, String seats,
                                                   TicketVietJetRepository ticketVietjetRepository) {
        if (ticketVietjetRepository.checkExistTicket(reservationCode, flightCode, seats)) {
            return ApiResponseStatus.TICKET_EXIST;
        }

        return ApiResponseStatus.SUCCESS;
    }

    public static ApiResponseStatus validateConfirmCustomer(TicketConfirmRequest request,
                                                            CustomerRepository customerRepository) {
        String customerId = request.getCustomerId();
        String ticketId = request.getTicketId();
        String fistNameCustomer = request.getFistNameCustomer();
        String lastNameCustomer = request.getLastNameCustomer();
        String birthDate = request.getBirthDateCustomer();
        Date birthDateCustomer = DateUtils.parseDate(birthDate);

        if (Boolean.FALSE.equals(request.getIsCustomerVietJet())) {
            return ApiResponseStatus.CUSTOMER_NOT_VIET_JET;
        }

        Customer customerInfo = customerRepository.findById(customerId);
        LOGGER.info("customerInfo: {}", customerInfo);
        if (customerInfo == null) {
            return ApiResponseStatus.NOT_FOUND_CUSTOMER;
        }

        if (customerRepository.checkInfoCustomer(ticketId, customerId)) {
            return ApiResponseStatus.SUCCESS;
        }

        boolean exists = customerRepository.checkInfoCustomer(ticketId, fistNameCustomer,
                lastNameCustomer, birthDateCustomer);
        if (!exists) {
            return ApiResponseStatus.INVALID_CUSTOMER;
        }

        if (customerRepository.checkCustomerUsedTicket(customerId)) {
            return ApiResponseStatus.USED_TICKET_ERROR;
        }

        if (customerRepository.checkTicketExist(ticketId)) {
            return ApiResponseStatus.TICKET_EXIST;
        }

        return ApiResponseStatus.SUCCESS;
    }

    public static ApiResponseStatus validateCheckPrize(CustomerPrizeRequest request,
                                                       CustomerRepository customerRepository,
                                                       PrizeRepository prizeRepository) {
        String customerId = request.getCustomerId();
        Customer customer = customerRepository.findById(customerId);
        LOGGER.info("Customer {}" , customer);
        if (customer == null) {
            return ApiResponseStatus.NOT_FOUND_CUSTOMER;
        }

        if (LocalDateTime.now().isAfter(Constant.VJ_E_SKY_ONE_END_DATE)) {
            return ApiResponseStatus.PROGRAM_ENDED;
        }

        if (customer.getCreatedAt().isBefore(Constant.VJ_E_SKY_ONE_START_DATE)
                || customer.getCreatedAt().isAfter(Constant.VJ_E_SKY_ONE_END_DATE)) {
            return ApiResponseStatus.NOT_ENOUGH_CONDITION_FOR_PRIZE;
        }

        if (Boolean.FALSE.equals(prizeRepository.checkExistPrize(customerId))) {
            return ApiResponseStatus.NO_PRIZE_CODE;
        }

        return ApiResponseStatus.SUCCESS;
    }

    public static ApiResponseStatus validateUpdatePrize(InfoPrizeRequest request,
                                                        CustomerRepository customerRepository,
                                                        PrizeRepository prizeRepository) {
        String customerId = request.getCustomerId();
        Customer customer = customerRepository.findById(customerId);

        if (customer == null) {
            return ApiResponseStatus.NOT_FOUND_CUSTOMER;
        }

        boolean checkResult = prizeRepository.checkResultPrize(request);
        if (!checkResult) {
            return ApiResponseStatus.PRIZE_CODE_ERROR;
        }

        boolean checkUsed = prizeRepository.checkUsedPrize(request);
        if (checkUsed) {
            return ApiResponseStatus.USED_PRIZE;
        }
        LocalDate prizeDrawDay = LocalDate.parse(request.getPrizeDrawDay(), DateTimeFormatter.ISO_LOCAL_DATE);
        boolean updated = prizeRepository.updateResultPrize(request, prizeDrawDay);
        if (!updated)
            return ApiResponseStatus.UPDATE_PRIZE_ERROR;

        return ApiResponseStatus.SUCCESS;
    }

}
