package com.vladislav.logger.controllers.view;

import com.vladislav.logger.dao.ReportDAO;
import com.vladislav.logger.dao.SuiteDAO;
import com.vladislav.logger.models.Report;
import com.vladislav.logger.models.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/suites")
public class ViewSuiteController {

    private final SuiteDAO suiteDAO;
    private final ReportDAO reportDAO;

    @Autowired
    public ViewSuiteController(SuiteDAO suiteDAO, ReportDAO reportDAO) {
        this.suiteDAO = suiteDAO;
        this.reportDAO = reportDAO;
    }

    @GetMapping()
    public String getSuites(@RequestParam("build") String build,
                            @RequestParam("day") String day,
                            @RequestParam("month") String month,
                            @RequestParam("year") String year,
                            Model model){
        List<Suite> suites =  suiteDAO.getSuites(build, day, month, year);
        model.addAttribute("suiteIds", getIdsFromSuites(suites));
        model.addAttribute("suites", suites);

        Report report = reportDAO.getReportBySuiteIDs(getIdsFromSuites(suites));
        model.addAttribute("report", report);
        model.addAttribute("passrate", getPassRate(report));
        return "vladislav/suites";
    }

    private String getPassRate(Report report){
        double passed = report.getPassedTests().size();
        double totalFinished = report.getFinishedTests().size();
        return String.format("%.2f", passed / totalFinished * 100);
    }

    private String getIdsFromSuites(List<Suite> suites){
        StringBuilder sb = null;
        for (Suite suite : suites){
            if (sb == null){
                sb = new StringBuilder(String.valueOf(suite.getId()));
            } else {
                sb.append(",").append(suite.getId());
            }
        }
        return sb.toString();
    }
}
