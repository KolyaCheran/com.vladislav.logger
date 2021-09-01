package com.vladislav.logger.dao;

import com.vladislav.logger.models.Suite;
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
public class SuiteDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SuiteDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createNewSuite(Suite suite){
        final String INSERT_SQL = "INSERT INTO suite (run_id, status, start_hour, start_minute, start_second, name) value (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[] {"id"});
                        ps.setInt(1, suite.getRunId());
                        ps.setString(2, suite.getStatus());
                        ps.setInt(3, suite.getStartHour());
                        ps.setInt(4, suite.getStartMinute());
                        ps.setInt(5, suite.getStartSecond());
                        ps.setString(6, suite.getName());
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void finishSuite(Suite suite){
        jdbcTemplate.update("UPDATE suite SET status=?, end_hour=?, end_minute=?, end_second=? WHERE id=?",
                suite.getStatus(),
                suite.getEndHour(),
                suite.getEndMinute(),
                suite.getEndSecond(),
                suite.getId());
    }
}
