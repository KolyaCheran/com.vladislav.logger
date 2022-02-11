package com.vladislav.logger.dao;

import com.vladislav.logger.helpers.TimeHelper;
import com.vladislav.logger.models.Action;
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
public class ActionDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ActionDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createNewAction(Action action){
        final String INSERT_SQL = "INSERT INTO action (step_id, message, result, action_hour, action_minute, action_second, timestamp ) value (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[] {"id"});
                        ps.setInt(1, action.getStepId());
                        ps.setString(2, action.getMessage());
                        ps.setString(3, action.getResult());
                        ps.setInt(4, action.getActionHour());
                        ps.setInt(5, action.getActionMinute());
                        ps.setInt(6, action.getActionSecond());
                        ps.setInt(7, (int) TimeHelper.getUnixTimeStamp());
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void updateActionError(Action action){
        jdbcTemplate.update("UPDATE action SET error=?, result=? WHERE id=?",
                action.getError(), action.getResult(), action.getId());
    }

    public void removeOldActions(int timestamp){
        final String DELETE_SQL = "DELETE FROM action where timestamp<?";
        jdbcTemplate.update(DELETE_SQL, timestamp);
    }
}
