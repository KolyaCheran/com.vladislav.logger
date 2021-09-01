package com.vladislav.logger.dao;

import com.vladislav.logger.models.Run;
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
public class RunDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RunDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createRun(Run run){
        final String INSERT_SQL = "INSERT INTO run (build, day, month, year) value (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[] {"id"});
                        ps.setString(1, run.getBuild());
                        ps.setInt(2, run.getDay());
                        ps.setInt(3, run.getMonth());
                        ps.setInt(4, run.getYear());
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();
    }
}
