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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, List<Integer>> getRunForDate(int day, int month, int year){
        return getRunsAndIds(jdbcTemplate.queryForList("SELECT build, id FROM run where year=? and month=? and day=?",
                year, month, day));
    }

    public Map<String, List<Integer>> getRunForDateAndBuildName(int day, int month, int year, String buildName){
        return getRunsAndIds(jdbcTemplate.queryForList("SELECT build, id FROM run where year=? and month=? and day=? and build=?",
                year, month, day, buildName));
    }

    private Map<String, List<Integer>> getRunsAndIds(List<Map<String, Object>> runs){
        Map<String, List<Integer>> uniqueRuns = new HashMap<>();
        runs.stream().forEach(val -> {
            if (!uniqueRuns.containsKey(String.valueOf(val.get("build")))) {
                uniqueRuns.put(String.valueOf(val.get("build")), new ArrayList<>());
            }
            uniqueRuns.get(String.valueOf(val.get("build"))).add((Integer) val.get("id"));
        });
        return uniqueRuns;
    }
}
