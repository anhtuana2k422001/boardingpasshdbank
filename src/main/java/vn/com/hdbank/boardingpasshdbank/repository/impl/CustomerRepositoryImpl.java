package vn.com.hdbank.boardingpasshdbank.repository.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.request.TicketConfirmRequest;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;

import java.util.List;


@Repository
@AllArgsConstructor
@Slf4j
public class CustomerRepositoryImpl implements CustomerRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void updateCustomerTypeById(String customerType, int id) {
        String sql = "UPDATE customer SET customer_type=? WHERE id=?";
        try {
            jdbcTemplate.update(sql, customerType, id);
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        try{
            List<Customer> customers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class), id);
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
            List<Customer> customers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class), phoneNumber);
            return customers.isEmpty() ? null : customers.get(0);
        }catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public boolean usedTicket(TicketConfirmRequest request) {
        String sql = "SELECT COUNT(*) FROM ticket_vietjet WHERE reservation_code = ? AND customer_id = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, request.getReservationCode(), request.getCustomerId());
            return count != null && count > 0;
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

}
