package com.vladislav.logger.dao;

import com.vladislav.logger.models.Report;
import com.vladislav.logger.models.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ReportDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReportDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Report getReportBySuiteIDs(String ids) {
        Report report = new Report();
        List<Test> allTests = getTests(ids);
        report.setAllTests(allTests);
        report.setPassedTests(getTestsByResult(allTests, "passed"));
        report.setFailedTests(getTestsByResult(allTests, "failed"));
        report.setSkippedTests(getTestsByResult(allTests, "skipped"));
        report.setFinishedTests(getTestsByStatus(allTests, true));
        report.setNotFinishedTests(getTestsByStatus(allTests, false));
        report.setSuiteIds(ids);
        return report;
    }

    private List<Test> getTestsByResult(List<Test> allTests, String result) {
        List<Test> resultList = new ArrayList<>();
        for (Test test : allTests) {
            if (test.getResult().equalsIgnoreCase(result) && test.getStatus().equalsIgnoreCase("finish")){
                resultList.add(test);
            }
        }
        return resultList;
    }

    private List<Test> getTestsByStatus(List<Test> allTests, boolean finished) {
         List<Test> statusList = new ArrayList<>();
        for (Test test : allTests) {
            if (finished && test.getStatus().equalsIgnoreCase("finish")){
                statusList.add(test);
            } else if (!finished && !test.getStatus().equalsIgnoreCase("finish")){
                statusList.add(test);
            }
        }
        return statusList;
    }

    private List<Test> getTests(String ids) {
        String inSql = String.join(",", Collections.nCopies(ids.split(",").length, "?"));

        List<Test> tests = jdbcTemplate.query(
                String.format("SELECT * FROM test WHERE suite_id IN (%s)", inSql),
                (rs, rowNum) -> new Test(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("status"),
                        rs.getString("result"),
                        rs.getInt("start_hour"),
                        rs.getInt("start_minute"),
                        rs.getInt("start_second"),
                        rs.getInt("end_hour"),
                        rs.getInt("end_minute"),
                        rs.getInt("end_second")), ids.split(","));
        return tests;
    }

    public List<Test> getFailedTests(String ids) {
        String inSql = String.join(",", Collections.nCopies(ids.split(",").length, "?"));

        List<Test> tests = jdbcTemplate.query(
                String.format("SELECT * FROM test WHERE suite_id IN (%s) AND result='failed'", inSql),
                (rs, rowNum) -> new Test(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("status"),
                        rs.getString("result"),
                        rs.getInt("start_hour"),
                        rs.getInt("start_minute"),
                        rs.getInt("start_second"),
                        rs.getInt("end_hour"),
                        rs.getInt("end_minute"),
                        rs.getInt("end_second")), ids.split(","));
        return tests;
    }

    public List<Test> getSkippedTests(String ids) {
        String inSql = String.join(",", Collections.nCopies(ids.split(",").length, "?"));

        List<Test> tests = jdbcTemplate.query(
                String.format("SELECT * FROM test WHERE suite_id IN (%s) AND result='skipped'", inSql),
                (rs, rowNum) -> new Test(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("status"),
                        rs.getString("result"),
                        rs.getInt("start_hour"),
                        rs.getInt("start_minute"),
                        rs.getInt("start_second"),
                        rs.getInt("end_hour"),
                        rs.getInt("end_minute"),
                        rs.getInt("end_second")), ids.split(","));
        return tests;
    }
}
