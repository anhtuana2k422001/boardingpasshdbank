package vn.com.hdbank.boardingpasshdbank.repository.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.entity.TicketVietJet;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietJetRepository;

import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class TicketVietJetRepositoryImpl implements TicketVietJetRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void create(TicketVietJet ticketVietjet) {
        String sql = "INSERT INTO ticket_vietjet (last_name, first_name, flight_code, reservation_code, seats, customer_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
             jdbcTemplate.update(sql, ticketVietjet.getLastName(), ticketVietjet.getFirstName(), ticketVietjet.getFlightCode(), ticketVietjet.getReservationCode(), ticketVietjet.getSeats(), null);
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public void updateCustomerIdByFlightCode(int customerId, String reservationCode) {
        String sql = "UPDATE ticket_vietjet SET customer_id = ? WHERE reservation_code = ?";
        try {
            jdbcTemplate.update(sql, customerId, reservationCode);
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public List<TicketVietJet> findCustomerIdNotNull(String reservationCode) {
        String sql = "SELECT * FROM ticket_vietjet WHERE reservation_code = ? AND customer_id IS NOT NULL";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TicketVietJet.class), reservationCode);
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
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
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

}
