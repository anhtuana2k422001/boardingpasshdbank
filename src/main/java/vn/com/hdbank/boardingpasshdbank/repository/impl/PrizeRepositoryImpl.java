package vn.com.hdbank.boardingpasshdbank.repository.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.Prize;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.InfoPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.repository.PrizeRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
@AllArgsConstructor
public class PrizeRepositoryImpl implements PrizeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final Random rand = new Random();

    @Override
    public List<Prize> findByCustomerId(int customerId){
        String sql = "SELECT * FROM prize WHERE customer_id = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Prize.class), customerId);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void save(Prize prize) {
        String sql = "INSERT INTO prize (prize_code, prize_amount, customer_id, used) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, prize.getPrizeCode(), prize.getPrizeAmount(), prize.getCustomerId(), prize.isUsed());
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String generatePrizeCode() {
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
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean checkExistsPrizeCodeForVietJet(int customerId) {
        String sql = "SELECT COUNT(*) FROM prize WHERE customer_id = ? AND prize_code LIKE 'VJ%';";
        try {
            int count = Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{customerId}, Integer.class)).orElse(0);
            return count > 0;
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean updateResultPrize(InfoPrizeRequest request, int customerId) {
        // TODO: CHECK prizeCode
        String sql = "UPDATE prize SET prize_amount = ?, used = ? WHERE prize_code = ? AND customer_id = ?";
        int count = jdbcTemplate.update(sql, request.getTotalAmount(), Boolean.TRUE, request.getPrizeCode(), customerId);
        return count > 0;
    }

}
