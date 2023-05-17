package vn.com.hdbank.boardingpasshdbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietjetRepository;

public class BaseService {
    @Autowired
    protected TicketVietjetRepository ticketVietjetRepository;
}
