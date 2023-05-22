package vn.com.hdbank.boardingpasshdbank.repository.impl;

import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietJet;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietJetRepository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TicketVietJetRepositoryImpl implements TicketVietJetRepository {
    private final JdbcTemplate jdbcTemplate;

    public void create(TicketVietJet ticketVietjet) {
        String sql = "INSERT INTO ticket_vietjet (last_name, first_name, flight_code, reservation_code, seats, customer_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, ticketVietjet.getLastName(), ticketVietjet.getFirstName(), ticketVietjet.getFlightCode(), ticketVietjet.getReservationCode(), ticketVietjet.getSeats(), null);
        } catch (DuplicateKeyException e) {
            throw new CustomException(ApiResponseStatus.DUPLICATED);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void updateCustomerIdByFlightCode(int customerId, String flightCode) {
        String sql = "UPDATE ticket_vietjet SET customer_id = ? WHERE flight_code = ?";
        try {
            jdbcTemplate.update(sql, customerId, flightCode);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("deprecation")
    public List<TicketVietJet> findCustomerIdNotNull(String flightCode) {
        String sql = "SELECT * FROM ticket_vietjet WHERE flight_code = ? AND customer_id IS NOT NULL";
        try {
            return jdbcTemplate.query(sql, new Object[]{flightCode}, new BeanPropertyRowMapper<>(TicketVietJet.class));
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @SuppressWarnings("deprecation")
    @Override
    public boolean checkExistsByFlightCode(String flightCode) {
        String sql = "SELECT COUNT(*) FROM ticket_vietjet WHERE flight_code = ?";
        try {
            int count = Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{flightCode},
                    Integer.class)).orElse(0);
            return count > 0;
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
