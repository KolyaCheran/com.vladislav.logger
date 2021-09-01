package com.vladislav.logger.dao;

import com.vladislav.logger.models.Test;
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
public class TestDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TestDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createNewTest(Test test){
        final String INSERT_SQL = "INSERT INTO test (name, suite_id, result) value (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[] {"id"});
                        ps.setString(1, test.getName());
                        ps.setInt(2, test.getSuiteId());
                        ps.setString(3, test.getResult());
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void updateTestOnStart(Test test){
        jdbcTemplate.update("UPDATE test SET " +
                "start_day=?, " +
                "start_month=?," +
                "start_hour=?," +
                "start_minute=?," +
                "start_second=?," +
                "result=?," +
                "status=?" +
                "WHERE id=?",
                test.getStartDay(), test.getStartMonth(), test.getStartHour(), test.getStartMinute(),
                test.getStartSecond(), test.getResult(), test.getStatus(), test.getId());
    }

    public void updateTestOnFinish(Test test){
        jdbcTemplate.update("UPDATE test SET " +
                        "end_day=?, " +
                        "end_month=?," +
                        "end_hour=?," +
                        "end_minute=?," +
                        "end_second=?," +
                        "result=?," +
                        "status=?" +
                        "WHERE id=?",
                test.getEndDay(), test.getEndMonth(), test.getEndHour(), test.getEndMinute(),
                test.getEndSecond(), test.getResult(), test.getStatus(), test.getId());
    }
}
