package vn.com.hdbank.boardingpasshdbank.repository.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.Prize;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.InfoPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.repository.PrizeRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
@AllArgsConstructor
@Slf4j
public class PrizeRepositoryImpl implements PrizeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final Random rand = new Random();

    @Override
    public List<Prize> findByCustomerId(int customerId) {
        String sql = "SELECT * FROM prize WHERE customer_id = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Prize.class), customerId);
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public void save(Prize prize) {
        String sql = "INSERT INTO prize (prize_code, prize_amount, customer_id, used) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, prize.getPrizeCode(), prize.getPrizeAmount(), prize.getCustomerId(), prize.isUsed());
        }
        catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public String generatePrizeCode() {
        try {
            String prizeCode = null;
            boolean isCodeUnique = false;
            while (!isCodeUnique) {
                int randomNumber = rand.nextInt(1000000);
                prizeCode = "VJ" + String.format("%06d", randomNumber);
                String sql = "SELECT COUNT(*) FROM prize WHERE prize_code = ?";
                int count = Optional.ofNullable(jdbcTemplate.queryForObject(sql, Integer.class, prizeCode)).orElse(0);
                if (count == 0) {
                    isCodeUnique = true;
                }
            }
            return prizeCode;
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public boolean checkExistsPrizeCodeForVietJet(int customerId) {
        String sql = "SELECT COUNT(*) FROM prize WHERE customer_id = ? AND prize_code LIKE 'VJ%'";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, customerId);
            return count != null && count > 0;
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public boolean updateResultPrize(InfoPrizeRequest request) {
        try {
            String sql = "UPDATE prize SET prize_amount = ?, used = ? WHERE prize_code = ? AND customer_id = ?";
            int count = jdbcTemplate.update(sql, request.getTotalAmount(), Boolean.TRUE, request.getPrizeCode(), request.getCustomerId());
            return count > 0;
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public boolean checkResultPrize(InfoPrizeRequest request) {
        String sql = "SELECT COUNT(*) FROM prize WHERE customer_id = ? AND prize_code = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
                    request.getCustomerId(), request.getPrizeCode());
            return count != null && count > 0;
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public boolean checkUsedPrize(InfoPrizeRequest request) {
        String sql = "SELECT COUNT(*)  FROM prize WHERE customer_id = ? AND prize_code = ? AND used = true";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
                    request.getCustomerId(), request.getPrizeCode());
            return count != null && count > 0;
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

}
