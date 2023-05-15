package vn.com.hdbank.boardingpasshdbank.repository.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateCustomerTypeById(String customerType,int id) {
        String sql = "UPDATE customer SET customer_type=? WHERE id=?";
        try {
            jdbcTemplate.update(sql, customerType,id);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new Customer(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("birth_date"),
                    rs.getString("customer_type"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            ));
        } catch (EmptyResultDataAccessException e) {
            throw new CustomException(ApiResponseStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
