package vn.com.hdbank.boardingpasshdbank.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.entity.Passenger;
import vn.com.hdbank.boardingpasshdbank.repository.PassengerRepository;
import vn.com.hdbank.boardingpasshdbank.utils.WriteLog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PassengerRepositoryImpl implements PassengerRepository {

    private static final String CLASS_NAME = PassengerRepositoryImpl.class.getSimpleName();
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PassengerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Passenger passenger) {
        String sql = "INSERT INTO passenger (first_name, last_name, birth_date, customer_type, created_at) VALUES (?, ?, ?, ?, NOW())";
        try {
            jdbcTemplate.update(sql, passenger.getFirstName(), passenger.getLastName(), passenger.getBirthDate(), passenger.getCustomerType());
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Passenger> getAll() {
        String sql = "SELECT id, first_name, last_name, birth_date, customer_type, created_at FROM passenger";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new Passenger(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("birth_date"),
                    rs.getString("customer_type"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            ));
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void update(Passenger passenger) {
        String sql = "UPDATE passenger SET first_name=?, last_name=?, birth_date=?, customer_type=? WHERE id=?";
        try {
            jdbcTemplate.update(sql, passenger.getFirstName(), passenger.getLastName(), passenger.getBirthDate(), passenger.getCustomerType(), passenger.getId());
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void delete(int passengerId) {
        String sql = "DELETE FROM passenger WHERE id=?";
        try {
            jdbcTemplate.update(sql, passengerId);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
