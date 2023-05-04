package vn.com.hdbank.boardingpasshdbank.repository.impl;

import vn.com.hdbank.boardingpasshdbank.model.helloworld.HelloWorld;
import vn.com.hdbank.boardingpasshdbank.repository.HelloWorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HelloWorldRepositoryImpl implements HelloWorldRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HelloWorldRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void create(HelloWorld helloWorld) {
        jdbcTemplate.update("INSERT INTO HelloWorld (content) VALUES (?)", helloWorld.getContent());
    }

    @Override
    public HelloWorld findById(int id) {
        String sql = "SELECT * FROM HelloWorld WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new HelloWorldRowMapper());
    }

    @Override
    public List<HelloWorld> findAll() {
        return jdbcTemplate.query("SELECT * FROM HelloWorld", new HelloWorldRowMapper());
    }

    @Override
    public void update(HelloWorld helloWorld) {
        jdbcTemplate.update("UPDATE HelloWorld SET content = ? WHERE id = ?", helloWorld.getContent(), helloWorld.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM HelloWorld WHERE id = ?", id);
    }

    private static final class HelloWorldRowMapper implements RowMapper<HelloWorld> {

        @Override
        public HelloWorld mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String content = rs.getString("content");
            return new HelloWorld(id, content);
        }
    }
}