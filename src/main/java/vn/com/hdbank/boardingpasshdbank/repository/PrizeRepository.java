package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.model.entity.Prize;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.InfoPrizeRequest;

import java.util.List;


public interface PrizeRepository {
    List<Prize> findByCustomerId(int customerId);
    void save(Prize prize);
    String generatePrizeCode();
    boolean checkExistsPrizeCodeForVietjet(int customerId);
    boolean updateResultPrize(InfoPrizeRequest request, int customerId);
}