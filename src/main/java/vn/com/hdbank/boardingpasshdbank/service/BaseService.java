package vn.com.hdbank.boardingpasshdbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietJetRepository;

public class BaseService {
    @Autowired
    protected TicketVietJetRepository ticketVietjetRepository;
}
