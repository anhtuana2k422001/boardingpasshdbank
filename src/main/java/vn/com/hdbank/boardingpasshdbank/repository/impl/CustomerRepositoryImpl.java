package vn.com.hdbank.boardingpasshdbank.repository.impl;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;


@Repository
@AllArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void updateCustomerTypeById(String customerType, int id) {
        String sql = "UPDATE customer SET customer_type=? WHERE id=?";
        try {
            jdbcTemplate.update(sql, customerType, id);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Customer.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
