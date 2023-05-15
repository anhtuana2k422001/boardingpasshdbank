package vn.com.hdbank.boardingpasshdbank.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.Prize;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.CustomerPrizeRequest;
import vn.com.hdbank.boardingpasshdbank.repository.PrizeRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class PrizeRepositoryImpl implements PrizeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PrizeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Prize> findAll() {
        String sql = "SELECT * FROM prize";
        try {
            return jdbcTemplate.query(sql, new PrizeRowMapper());
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Prize> findById(int id) {
        String sql = "SELECT * FROM prize WHERE id = ?";
        try {
            List<Prize> prizes = jdbcTemplate.query(sql, new Object[]{id}, new PrizeRowMapper());
            return prizes.stream().findFirst();
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Prize> findByCustomerId(int customerId) {
        String sql = "SELECT * FROM prize WHERE customer_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{customerId}, new PrizeRowMapper());
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

    private static class PrizeRowMapper implements RowMapper<Prize> {
        @Override
        public Prize mapRow(ResultSet rs, int rowNum) throws SQLException {
            Prize prize = new Prize();
            prize.setId(rs.getInt("id"));
            prize.setPrizeCode(rs.getString("prize_code"));
            prize.setPrizeAmount(rs.getBigDecimal("prize_amount"));
            prize.setCustomerId(rs.getInt("customer_id"));
            prize.setUsed(rs.getBoolean("used"));
            prize.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return prize;
        }
    }

    public String generatePrizeCode() {
        String prizeCode = null;
        boolean isCodeUnique = false;
        while (!isCodeUnique) {
            Random rand = new Random();
            int randomNumber = rand.nextInt(1000000);
            prizeCode = "VJ" + String.format("%06d", randomNumber);
            String sql = "SELECT COUNT(*) FROM prize WHERE prize_code = ?";
            int count = jdbcTemplate.queryForObject(sql, Integer.class, prizeCode);
            if (count == 0) {
                isCodeUnique = true;
            }
        }
        return prizeCode;
    }

    @Override
    public boolean checkExistsPrizeCodeForVietjet(int customerId) {
        String sql = "SELECT COUNT(*) FROM prize WHERE customer_id = ? AND prize_code LIKE 'VJ%';";
        try {
            int count = jdbcTemplate.queryForObject(sql, new Object[]{customerId}, Integer.class);
            return count > 0;
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean updateResultPrize(CustomerPrizeRequest request, int customerId) {
        String sql = "UPDATE prize SET prize_amount = ?, used = ? WHERE prize_code = ? AND customer_id = ?";
        int count = jdbcTemplate.update(sql, request.getTotalAmount(), true, request.getPrizeCode(), customerId);
        return count > 0;
    }

}
