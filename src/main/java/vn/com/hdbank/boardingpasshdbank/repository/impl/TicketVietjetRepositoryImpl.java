package vn.com.hdbank.boardingpasshdbank.repository.impl;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietjet;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietjetRepository;

import java.util.List;

@Repository
public class TicketVietjetRepositoryImpl implements TicketVietjetRepository {
    private final JdbcTemplate jdbcTemplate;

    public TicketVietjetRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(TicketVietjet ticketVietjet) {
        String sql = "INSERT INTO ticket_vietjet (last_name, first_name, flight_code, reservation_code, seats, customer_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            if (ticketVietjet.getCustomerId() > 0) {
                jdbcTemplate.update(sql, ticketVietjet.getLastName(), ticketVietjet.getFirstName(), ticketVietjet.getFlightCode(), ticketVietjet.getReservationCode(), ticketVietjet.getSeats(), ticketVietjet.getCustomerId());

            } else {
                jdbcTemplate.update(sql, ticketVietjet.getLastName(), ticketVietjet.getFirstName(), ticketVietjet.getFlightCode(), ticketVietjet.getReservationCode(), ticketVietjet.getSeats(), null);

            }
        } catch (DuplicateKeyException e) {
            throw new CustomException(ApiResponseStatus.CONFLICT);
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

    public List<TicketVietjet> findByFlightCodeAndPassengerIdIsNotNull(String flightCode) {
        String sql = "SELECT * FROM ticket_vietjet WHERE flight_code = ? AND customer_id IS NOT NULL";
        try {
            return jdbcTemplate.query(sql, new Object[]{flightCode}, (rs, rowNum) -> new TicketVietjet(
                    rs.getInt("id"),
                    rs.getString("last_name"),
                    rs.getString("first_name"),
                    rs.getString("flight_code"),
                    rs.getString("reservation_code"),
                    rs.getString("seats"),
                    rs.getInt("customer_id"),
                    rs.getTimestamp("created_at").toLocalDateTime())
            );
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean checkExistsByFlightCode(String flightCode) {
        String sql = "SELECT COUNT(*) FROM ticket_vietjet WHERE flight_code = ?";
        try {
            int count = jdbcTemplate.queryForObject(sql, new Object[]{flightCode}, Integer.class);
            return count > 0;
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
