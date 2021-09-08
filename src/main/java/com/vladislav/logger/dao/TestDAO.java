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
import java.util.List;

@Component
public class TestDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TestDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createNewTest(Test test){
        final String INSERT_SQL = "INSERT INTO test (name, suite_id, result, status) value (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[] {"id"});
                        ps.setString(1, test.getName());
                        ps.setInt(2, test.getSuiteId());
                        ps.setString(3, test.getResult());
                        ps.setString(4, test.getStatus());
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void updateTestOnStart(Test test){
        jdbcTemplate.update("UPDATE test SET " +
                "start_hour=?," +
                "start_minute=?," +
                "start_second=?," +
                "result=?," +
                "status=?" +
                "WHERE id=?",
                test.getStartHour(), test.getStartMinute(),
                test.getStartSecond(), test.getResult(), test.getStatus(), test.getId());
    }

    public void updateTestResult(Test test){
        jdbcTemplate.update("UPDATE test SET result=? WHERE id=?", test.getResult(), test.getId());
    }

    public void updateTestOnFinish(Test test){
        jdbcTemplate.update("UPDATE test SET " +
                        "end_hour=?," +
                        "end_minute=?," +
                        "end_second=?," +
                        "result=?," +
                        "status=?" +
                        "WHERE id=?",
                test.getEndHour(), test.getEndMinute(),
                test.getEndSecond(), test.getResult(), test.getStatus(), test.getId());
    }

    public List<Test> getTest(int id) {
        List<Test> tests = jdbcTemplate.query("SELECT * FROM test WHERE suite_id=?",
                (rs, rowNum) -> new Test(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("status"),
                        rs.getString("result"),
                        rs.getInt("start_hour"),
                        rs.getInt("start_minute"),
                        rs.getInt("start_second"),
                        rs.getInt("end_hour"),
                        rs.getInt("end_minute"),
                        rs.getInt("end_second")), new Integer[] {id});
        return tests;
    }
}
