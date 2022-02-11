package com.vladislav.logger.dao;

import com.vladislav.logger.helpers.TimeHelper;
import com.vladislav.logger.models.Action;
import com.vladislav.logger.models.Attachment;
import com.vladislav.logger.models.Step;
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
        final String INSERT_SQL = "INSERT INTO test (name, suite_id, result, status, timestamp) value (?, ?, ?, ?, ?)";
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
                        ps.setInt(5, (int)TimeHelper.getUnixTimeStamp());
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

    public List<Test> getTests(int suiteId) {
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
                        rs.getInt("end_second")), new Integer[] {suiteId});
        return tests;
    }

    public Test getTest(int testId) {
        List<Test> tests = jdbcTemplate.query("SELECT * FROM test WHERE id=? ORDER BY id ASC",
                (rs, rowNum) -> new Test(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("status"),
                        rs.getString("result"),
                        rs.getInt("start_hour"),
                        rs.getInt("start_minute"),
                        rs.getInt("start_second"),
                        rs.getInt("end_hour"),
                        rs.getInt("end_minute"),
                        rs.getInt("end_second")),
                new Integer[]{testId});

        tests.get(0).setSteps(getListOfSteps(testId));
        return tests.get(0);
    }

    private List<Step> getListOfSteps(int testId){
        List<Step> steps = jdbcTemplate.query("SELECT * FROM step WHERE test_id=? ORDER BY id ASC",
                (rs, rowNun) -> new Step(rs.getInt("id"),
                        rs.getString("result"),
                        rs.getString("message")),
                new Integer[]{testId});

        for(Step step : steps){
            step.setActions(getListOfActions(step.getId()));
        }
        return steps;
    }

    private List<Action> getListOfActions(int stepId){
        List<Action> actions = jdbcTemplate.query("SELECT * FROM action WHERE step_id=? ORDER BY id ASC",
                (rs, rowNun) -> new Action(rs.getInt("id"),
                        rs.getString("message"),
                        rs.getString("error"),
                        rs.getString("result"),
                        rs.getInt("action_hour"),
                        rs.getInt("action_minute"),
                        rs.getInt("action_second")),
                new Integer[]{stepId});

        for(Action action : actions){
            action.setAttachments(getListOfAttachments(action.getId()));
        }
        return actions;
    }

    private List<Attachment> getListOfAttachments(int actionId){
        List<Attachment> attachments = jdbcTemplate.query("SELECT * FROM attachments WHERE action_id=? ORDER BY id ASC",
                (rs, rowNun) -> new Attachment(rs.getInt("id"),
                        rs.getString("location")),
                new Integer[]{actionId});
        return attachments;
    }
}
