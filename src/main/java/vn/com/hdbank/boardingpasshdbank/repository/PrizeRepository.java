package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.entity.Prize;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.InfoPrizeRequest;



public interface PrizeRepository {
    Prize getPrizeCustomer(String customerId);
    void savePrize(Prize prize);
    String generatePrizeCode();
    boolean checkExistPrize(String customerId);
    boolean updateResultPrize(InfoPrizeRequest request);
    boolean checkResultPrize(InfoPrizeRequest request);
    boolean checkUsedPrize(InfoPrizeRequest request);
}