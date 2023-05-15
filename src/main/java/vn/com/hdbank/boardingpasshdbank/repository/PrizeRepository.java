package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.model.entity.Prize;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.CustomerPrizeRequest;

import java.util.List;
import java.util.Optional;

public interface PrizeRepository {
    List<Prize> findAll();

    Optional<Prize> findById(int id);

    List<Prize> findByCustomerId(int customerId);

    void save(Prize prize);

    String generatePrizeCode();
    boolean checkExistsPrizeCodeForVietjet(int customerId);
    boolean updateResultPrize(CustomerPrizeRequest request, int customerId);

}