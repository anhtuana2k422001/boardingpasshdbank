package vn.com.hdbank.boardingpasshdbank.repository.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.anotation.MyModelRowMapper;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;

import java.util.Date;
import java.util.List;


@Repository
@AllArgsConstructor
@Slf4j
public class CustomerRepositoryImpl implements CustomerRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void updateCustomerVJ(String customerType, String customerId) {
        String sql = "UPDATE customer SET customer_type=? WHERE id=?";
        try {
            jdbcTemplate.update(sql, customerType, customerId);
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public Customer findById(String customerId) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        try{
            List<Customer> customers = jdbcTemplate.query(sql, new MyModelRowMapper<>(Customer.class), customerId);
            return customers.isEmpty() ? null : customers.get(0);
        }catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public Customer findByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM customer WHERE phone_number = ?";
        try{
            List<Customer> customers = jdbcTemplate.query(sql, new MyModelRowMapper<>(Customer.class), phoneNumber);
            return customers.isEmpty() ? null : customers.get(0);
        }catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public boolean checkInfoCustomer(String ticketId,  String fistNameCustomer, String lastNameCustomer, Date birthDateCustomer) {
        String sql = "SELECT COUNT(*) FROM ticket_vietjet WHERE id = ? AND first_name = ? AND last_name = ? AND birth_date = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, ticketId, fistNameCustomer, lastNameCustomer, birthDateCustomer);
            return count != null && count > 0;
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public boolean checkInfoCustomer(String ticketId, String customerId) {
        String sql = "SELECT COUNT(*) > 0 FROM ticket_vietjet WHERE id = ? AND customer_id = ? ";
        try {
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, ticketId, customerId));
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public boolean checkTicketExist(String ticketId) {
        String sql = "SELECT COUNT(*) > 0 FROM ticket_vietjet WHERE id = ? AND customer_id IS NOT NULL";
        try {
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, ticketId));
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public boolean checkCustomerUsedTicket(String customerId) {
        String sql = "SELECT EXISTS (SELECT * FROM ticket_vietjet WHERE customer_id = ?) AS customer_exists";
        try {
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, customerId));
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }


}
