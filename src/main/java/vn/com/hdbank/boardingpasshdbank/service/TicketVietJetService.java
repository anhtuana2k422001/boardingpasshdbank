package vn.com.hdbank.boardingpasshdbank.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import vn.com.hdbank.boardingpasshdbank.model.response.ResponseInfo;
import vn.com.hdbank.boardingpasshdbank.model.response.TicketVietJetInformation;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketRequest;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketScanRequest;

public interface TicketVietJetService {
    ResponseEntity<ResponseInfo<TicketVietJetInformation>>
    checkTicketVietJet(TicketRequest request, BindingResult bindingResult);
    ResponseEntity<ResponseInfo<TicketVietJetInformation>>
    checkScanTicketVietJet(TicketScanRequest request, BindingResult bindingResult);
}
