package vn.com.hdbank.boardingpasshdbank.repository.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietJet;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietJetRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TicketVietJetRepositoryImpl implements TicketVietJetRepository {
    private final JdbcTemplate jdbcTemplate;

    public void create(TicketVietJet ticketVietjet) {
        String sql = "INSERT INTO ticket_vietjet (last_name, first_name, flight_code, reservation_code, seats, customer_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
             jdbcTemplate.update(sql, ticketVietjet.getLastName(), ticketVietjet.getFirstName(), ticketVietjet.getFlightCode(), ticketVietjet.getReservationCode(), ticketVietjet.getSeats(), null);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    public void updateCustomerIdByFlightCode(int customerId, String reservationCode) {
        String sql = "UPDATE ticket_vietjet SET customer_id = ? WHERE reservation_code = ?";
        try {
            jdbcTemplate.update(sql, customerId, reservationCode);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    public List<TicketVietJet> findCustomerIdNotNull(String reservationCode) {
        String sql = "SELECT * FROM ticket_vietjet WHERE reservation_code = ? AND customer_id IS NOT NULL";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TicketVietJet.class), reservationCode);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public boolean checkExistsByFlightCode(String reservationCode) {
        String sql = "SELECT COUNT(*) FROM ticket_vietjet WHERE reservation_code = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, reservationCode);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

}