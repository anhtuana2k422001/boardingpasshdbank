package com.hoanhtuan.boardingpasshdbank.repository;

import com.hoanhtuan.boardingpasshdbank.model.helloworld.HelloWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HelloWorldRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HelloWorldRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void create(HelloWorld helloWorld) {
        jdbcTemplate.update("INSERT INTO HelloWorld (content) VALUES (?)", helloWorld.getContent());
    }

    public HelloWorld findById(int id) {
        String sql = "SELECT * FROM HelloWorld WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new HelloWorldRowMapper());
    }

    public List<HelloWorld> findAll() {
        return jdbcTemplate.query("SELECT * FROM HelloWorld", new HelloWorldRowMapper());
    }

    public void update(HelloWorld helloWorld) {
        jdbcTemplate.update("UPDATE HelloWorld SET content = ? WHERE id = ?", helloWorld.getContent(), helloWorld.getId());
    }

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