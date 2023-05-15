package vn.com.hdbank.boardingpasshdbank.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietjetRepository;

public class BaseService {
    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected TicketVietjetRepository ticketVietjetRepository;
}
