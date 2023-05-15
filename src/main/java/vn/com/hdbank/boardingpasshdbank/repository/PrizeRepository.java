package vn.com.hdbank.boardingpasshdbank.repository;

import vn.com.hdbank.boardingpasshdbank.model.entity.Prize;

import java.util.List;
import java.util.Optional;

public interface PrizeRepository {
    List<Prize> findAll();

    Optional<Prize> findById(int id);

    List<Prize> findByCustomerId(int customerId);

    void save(Prize prize);

    void update(Prize prize);

    void deleteById(int id);

    String generatePrizeCode();
    boolean checkExistsPrizeCodeForVietjet(int customerId);

}