package com.vladislav.logger.dao;

import com.vladislav.logger.helpers.Result;
import com.vladislav.logger.helpers.TimeHelper;
import com.vladislav.logger.models.Suite;
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
public class SuiteDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SuiteDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createNewSuite(Suite suite) {
        final String INSERT_SQL = "INSERT INTO suite (run_id, status, start_hour, start_minute, start_second, name, timestamp) value (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                        ps.setInt(1, suite.getRunId());
                        ps.setString(2, suite.getStatus());
                        ps.setInt(3, suite.getStartHour());
                        ps.setInt(4, suite.getStartMinute());
                        ps.setInt(5, suite.getStartSecond());
                        ps.setString(6, suite.getName());
                        ps.setInt(7, (int) TimeHelper.getUnixTimeStamp());
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void finishSuite(Suite suite) {
        jdbcTemplate.update("UPDATE suite SET status=?, end_hour=?, end_minute=?, end_second=? WHERE id=?",
                suite.getStatus(),
                suite.getEndHour(),
                suite.getEndMinute(),
                suite.getEndSecond(),
                suite.getId());
    }

    public void updateSuiteResult(Suite suite) {
        jdbcTemplate.update("UPDATE suite SET result=? WHERE id=?",
                suite.getResult(),
                suite.getId());
    }

    public void updateSuiteStatus(Suite suite) {
        jdbcTemplate.update("UPDATE suite SET status=? WHERE id=?",
                suite.getStatus(),
                suite.getId());
    }

    private void changeResultsForSuites(List<Suite> suites){ //results need to be updated on fly when page loaded because of auto-rerun tests
        for (Suite suite : suites){
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
                            rs.getInt("end_second")), new Integer[] {suite.getId()});
            Result result = getTheWorstResultOfTest(tests);
            Suite updatedSuite = new Suite();
            updatedSuite.setId(suite.getId());
            updatedSuite.setResult(result);
            updateSuiteResult(updatedSuite);
        }
    }

    private Result getTheWorstResultOfTest(List<Test> tests){
        Result resultForReturn = Result.NOT_RUN;
        for (Test test : tests){
            if(Result.getResultByText(test.getResult()).getResultIndex() > resultForReturn.getResultIndex()){
                resultForReturn = Result.getResultByText(test.getResult());
            }
        }
        return resultForReturn;
    }

    public List<Suite> getSuites(String buildName, String day, String month, String year) {
        String query = "SELECT * FROM run AS r RIGHT JOIN suite AS s ON r.id = s.run_id WHERE r.year=? AND r.month=? AND r.day=? AND build=?;";
        List<Suite> suites = jdbcTemplate.query(query,
                (rs, rowNun) -> new Suite(rs.getInt("s.id"),
                        rs.getString("name"),
                        rs.getString("status"),
                        rs.getString("result"),
                        rs.getInt("start_second"),
                        rs.getInt("start_minute"),
                        rs.getInt("start_hour"),
                        rs.getInt("end_second"),
                        rs.getInt("end_minute"),
                        rs.getInt("end_hour")),
                new Object[]{year, month, day, buildName});
        changeResultsForSuites(suites);
        suites = jdbcTemplate.query(query,
                (rs, rowNun) -> new Suite(rs.getInt("s.id"),
                        rs.getString("name"),
                        rs.getString("status"),
                        rs.getString("result"),
                        rs.getInt("start_second"),
                        rs.getInt("start_minute"),
                        rs.getInt("start_hour"),
                        rs.getInt("end_second"),
                        rs.getInt("end_minute"),
                        rs.getInt("end_hour")),
                new Object[]{year, month, day, buildName});
        return suites;
    }

    public void removeOldSuites(int timestamp){
        final String DELETE_SQL = "DELETE FROM suite where timestamp<?";
        jdbcTemplate.update(DELETE_SQL, timestamp);
    }
}
