package vn.com.hdbank.boardingpasshdbank.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.model.entity.TicketVietjet;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietjetRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TicketVietjetRepositoryImpl implements TicketVietjetRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TicketVietjetRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(TicketVietjet ticketVietjet) {
        String sql = "INSERT INTO ticket_vietjet (last_name, first_name, flight_code, reservation_code, seats, passenger_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, ticketVietjet.getLastName(), ticketVietjet.getFirstName(), ticketVietjet.getFlightCode(), ticketVietjet.getReservationCode(), ticketVietjet.getSeats(),null);
        } catch (DuplicateKeyException e) {
            throw new CustomException(ApiResponseStatus.CONFLICT);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<TicketVietjet> getAllTicketVietjets() {
        String sql = "SELECT id, last_name, first_name, flight_code, reservation_code, seats, passenger_id, created_at FROM ticket_vietjet";
        try {
            return jdbcTemplate.query(sql, new TicketVietjetRowMapper());
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void updateTicketVietjet(TicketVietjet ticketVietjet) {
        String sql = "UPDATE ticket_vietjet SET last_name = ?, first_name = ?, flight_code = ?, reservation_code = ?, seats = ?, passenger_id = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, ticketVietjet.getLastName(), ticketVietjet.getFirstName(), ticketVietjet.getFlightCode(), ticketVietjet.getReservationCode(), ticketVietjet.getSeats(), ticketVietjet.getPassengerId(), ticketVietjet.getId());
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteTicketVietjet(int id) {
        String sql = "DELETE FROM ticket_vietjet WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new CustomException(ApiResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<TicketVietjet> findByFlightCodeAndPassengerIdIsNotNull(String flightCode) {
        String sql = "SELECT * FROM ticket_vietjet WHERE flight_code = ? AND passenger_id IS NOT NULL";
        try {
            return jdbcTemplate.query(sql, new Object[]{flightCode}, new TicketVietjetRowMapper());

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


    public static class TicketVietjetRowMapper implements RowMapper<TicketVietjet> {

        @Override
        public TicketVietjet mapRow(ResultSet rs, int rowNum) throws SQLException {
            TicketVietjet ticket = new TicketVietjet();
            ticket.setId(rs.getInt("id"));
            ticket.setLastName(rs.getString("last_name"));
            ticket.setFirstName(rs.getString("first_name"));
            ticket.setFlightCode(rs.getString("flight_code"));
            ticket.setReservationCode(rs.getString("reservation_code"));
            ticket.setSeats(rs.getString("seats"));
            ticket.setPassengerId(rs.getInt("passenger_id"));
            ticket.setCreateAt(rs.getTimestamp("created_at").toLocalDateTime());
            return ticket;
        }
    }
}
