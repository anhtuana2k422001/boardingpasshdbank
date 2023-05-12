package vn.com.hdbank.boardingpasshdbank.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.Customer;
import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietjet;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;

import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private static final String CLASS_NAME = CustomerRepositoryImpl.class.getSimpleName();
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Customer customer) {
        String sql = "INSERT INTO customer (first_name, last_name, birth_date, customer_type, created_at) VALUES (?, ?, ?, ?, NOW())";
        try {
            jdbcTemplate.update(sql, customer.getFirstName(), customer.getLastName(), customer.getBirthDate(), customer.getCustomerType());
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Customer> getAll() {
        String sql = "SELECT id, first_name, last_name, birth_date, customer_type, created_at FROM customer";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new Customer(
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

    public void update(Customer customer) {
        String sql = "UPDATE customer SET first_name=?, last_name=?, birth_date=?, customer_type=? WHERE id=?";
        try {
            jdbcTemplate.update(sql, customer.getFirstName(), customer.getLastName(), customer.getBirthDate(), customer.getCustomerType(), customer.getId());
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void updateCustomerTypeById(String customerType,int id) {
        String sql = "UPDATE customer SET customer_type=? WHERE id=?";
        try {
            jdbcTemplate.update(sql, customerType,id);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void delete(int customerId) {
        String sql = "DELETE FROM customer WHERE id=?";
        try {
            jdbcTemplate.update(sql, customerId);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT id, first_name, last_name, birth_date, customer_type, created_at FROM customer WHERE id = ?";
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
