package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.entity.Prize;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.InfoPrizeRequest;

import java.time.LocalDate;


public interface PrizeRepository {
    Prize getPrizeCustomer(String customerId);
    void savePrize(Prize prize);
    String generatePrizeCode();
    boolean checkExistPrize(String customerId);
    boolean updateResultPrize(InfoPrizeRequest request, LocalDate prizeDrawDay);
    boolean checkResultPrize(InfoPrizeRequest request);
    boolean checkUsedPrize(InfoPrizeRequest request);
}