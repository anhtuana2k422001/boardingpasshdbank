package vn.com.hdbank.boardingpasshdbank.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietjet;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketConfirmRequest;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietjetRepository;
import vn.com.hdbank.boardingpasshdbank.service.BaseService;

import java.util.List;

@Service
public class CustomerServiceImpl extends BaseService {
    private static final Logger LOGGER  = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TicketVietjetRepository ticketVietjetRepository;

    public Customer confirmCustomerVietjet(TicketConfirmRequest ticketConfirmRequest, int customerId) {
        List<TicketVietjet> lstFindByFlightCodeAndPassengerIdIsNotNull = ticketVietjetRepository.findByFlightCodeAndPassengerIdIsNotNull(ticketConfirmRequest.getFlightCode());
        if (!lstFindByFlightCodeAndPassengerIdIsNotNull.isEmpty()) {
            LOGGER.error(ApiResponseStatus.TICKET_VIETJET_EXISTED_AND_ASSIGNED.getStatusMessage());
            throw new CustomException(ApiResponseStatus.TICKET_VIETJET_EXISTED_AND_ASSIGNED);
        }
        if (!ticketVietjetRepository.checkExistsByFlightCode(ticketConfirmRequest.getFlightCode())) {
            LOGGER.error(ApiResponseStatus.INVALID_TICKET.getStatusMessage());
            throw new CustomException(ApiResponseStatus.INVALID_TICKET);
        }

        if(!ticketConfirmRequest.isCustomerVietjet()){
            LOGGER.error(ApiResponseStatus.CUSTOMER_NOT_VIETJET.getStatusMessage());
            throw new CustomException(ApiResponseStatus.CUSTOMER_NOT_VIETJET);
        }
        //ticket set customerId reference to table Customer
        ticketVietjetRepository.updateCustomerIdByFlightCode(customerId,ticketConfirmRequest.getFlightCode());
        //update customertype
        customerRepository.updateCustomerTypeById("VJ",customerId);

        return customerRepository.findById(customerId);
    }


}
