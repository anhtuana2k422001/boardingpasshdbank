package vn.com.hdbank.boardingpasshdbank.repository.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.common.ApiResponseStatus;
import vn.com.hdbank.boardingpasshdbank.common.Constant;
import vn.com.hdbank.boardingpasshdbank.common.anotation.MyModelRowMapper;
import vn.com.hdbank.boardingpasshdbank.exception.CustomException;
import vn.com.hdbank.boardingpasshdbank.entity.TicketVietJet;
import vn.com.hdbank.boardingpasshdbank.model.response.TicketVietJetInformation;
import vn.com.hdbank.boardingpasshdbank.repository.TicketVietJetRepository;

import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class TicketVietJetRepositoryImpl implements TicketVietJetRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void createTicket(TicketVietJet ticketVietjet) {
        String sql = "INSERT INTO ticket_vietjet (last_name, first_name, birth_date, flight_time, flight_code, " +
                "reservation_code, seats, total_amount, customer_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
             jdbcTemplate.update(sql, ticketVietjet.getLastName(), ticketVietjet.getFirstName(),
                     ticketVietjet.getBirthDate(), ticketVietjet.getFlightTime(),
                     ticketVietjet.getFlightCode(), ticketVietjet.getReservationCode(),
                     ticketVietjet.getSeats(), ticketVietjet.getTotalAmount(), null);
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public String getTicketId(String reservationCode, String flightCode, String seats) {
        String sql = "SELECT * FROM ticket_vietjet WHERE reservation_code = ? AND flight_code = ? AND  seats = ?";
        try {
            List<TicketVietJet> ticketVietJet = jdbcTemplate.query(sql, new MyModelRowMapper<>(TicketVietJet.class),
                    reservationCode, flightCode, seats);
            return ticketVietJet.isEmpty() ? null : String.valueOf(ticketVietJet.get(0).getId());
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public void updateConfirmCustomer(String ticketId, String customerId) {
        String sql = "UPDATE ticket_vietjet SET customer_id = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, customerId, ticketId);
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public boolean checkExistCustomer(String reservationCode, String flightCode, String seats) {
        String sql = "SELECT COUNT(*) > 0 FROM ticket_vietjet WHERE reservation_code = ? AND flight_code = ? AND " +
                "seats = ? AND customer_id IS NOT NULL";
        try {
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, reservationCode, flightCode, seats));
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }

    @Override
    public TicketVietJet getTicketCustomer(String reservationCode, String flightCode, String seats) {
        return null;
    }

    @Override
    public boolean checkSaveTicket(String reservationCode, String flightCode, String seats) {
        String sql = "SELECT COUNT(*) FROM ticket_vietjet WHERE reservation_code = ? " +
                "AND flight_code = ? AND seats = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, reservationCode, flightCode, seats);
            return count != null && count > 0;
        } catch (Exception e) {
            LOGGER.error(Constant.ERROR, e);
            throw new CustomException(ApiResponseStatus.DATABASE_ERROR);
        }
    }


}
