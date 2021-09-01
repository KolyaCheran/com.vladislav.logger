package com.vladislav.logger.dao;

import com.vladislav.logger.models.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class StepDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StepDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createNewStep(Step step){
        final String INSERT_SQL = "INSERT INTO step (test_id, result, message) value (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[] {"id"});
                        ps.setInt(1, step.getTestId());
                        ps.setString(2, step.getResult());
                        ps.setString(3, step.getMessage());
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();
    }
}
