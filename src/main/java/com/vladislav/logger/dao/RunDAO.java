package com.vladislav.logger.dao;

import com.vladislav.logger.helpers.TimeHelper;
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
import java.util.*;

@Component
public class RunDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RunDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createRun(Run run){
        final String INSERT_SQL = "INSERT INTO run (build, day, month, year, timestamp) value (?, ?, ?, ?, ?)";
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
                        ps.setInt(5, (int) TimeHelper.getUnixTimeStamp());
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();
    }

    public Set<String> getRunForDate(int day, int month, int year){
        return getRunsAndIds(jdbcTemplate.queryForList("SELECT build FROM run where year=? and month=? and day=?",
                year, month, day));
    }

    public Set<String> getRunForDateAndBuildName(int day, int month, int year, String buildName){
        return getRunsAndIds(jdbcTemplate.queryForList("SELECT build FROM run where year=? and month=? and day=? and build=?",
                year, month, day, buildName));
    }

    private Set<String> getRunsAndIds(List<Map<String, Object>> runs){
        Set<String> uniqueRuns = new HashSet<>();
        runs.stream().forEach(val -> {
            uniqueRuns.add(String.valueOf(val.get("build")));
        });
        return uniqueRuns;
    }
}
