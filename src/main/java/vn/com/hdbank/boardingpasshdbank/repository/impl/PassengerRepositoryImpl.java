package vn.com.hdbank.boardingpasshdbank.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.boardingpasshdbank.controller.PassengerController;
import vn.com.hdbank.boardingpasshdbank.model.vietjet.PassengerInformation;
import vn.com.hdbank.boardingpasshdbank.repository.PassengerRepository;
import vn.com.hdbank.boardingpasshdbank.utils.WriteLog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PassengerRepositoryImpl implements PassengerRepository {

    private static final String CLASS_NAME = PassengerRepositoryImpl.class.getSimpleName();
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PassengerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PassengerInformation> findPassengerByFlightCodeUsed(String flightCode) {
        String sql = "SELECT * FROM PassengerInformation WHERE flight_code = ? AND is_used = true";
        return jdbcTemplate.query(sql, new Object[]{flightCode}, new PassengerInformationRowMapper());
    }

    @Override
    public void create(PassengerInformation passengerInformation) {
        try{
            String sql = "INSERT INTO PassengerInformation (full_name, birth_date, customer_type, flight_code, reservation_code, seats, totalamountticket) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    passengerInformation.getFullName(),
                    passengerInformation.getBirthDate(),
                    passengerInformation.getCustomerType(),
                    passengerInformation.getFlightCode(),
                    passengerInformation.getReservationCode(),
                    passengerInformation.getSeats(),
                    passengerInformation.getTotalAmountTicket());
        }catch (Exception ex){
            WriteLog.errorLog(CLASS_NAME,"create",ex.getMessage());
        }
    }

    public class PassengerInformationRowMapper implements RowMapper<PassengerInformation> {
        @Override
        public PassengerInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
            PassengerInformation passenger = new PassengerInformation();
            passenger.setId(rs.getInt("id"));
            passenger.setFullName(rs.getString("full_name"));
            passenger.setBirthDate(rs.getString("birth_date"));
            passenger.setCustomerType(rs.getString("customer_type"));
            passenger.setCreateAt(rs.getTimestamp("created_at"));
            passenger.setFlightCode(rs.getString("flight_code"));
            passenger.setReservationCode(rs.getString("reservation_code"));
            passenger.setSeats(rs.getString("seats"));
            passenger.setTotalAmountTicket(rs.getDouble("totalamountticket"));
            return passenger;
        }
    }
}
