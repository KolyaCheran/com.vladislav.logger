package com.vladislav.logger.controllers.view;

import com.vladislav.logger.dao.ReportDAO;
import com.vladislav.logger.models.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final ReportDAO reportDAO;

    @Autowired
    public ReportController(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    @GetMapping("/{ids}")
    public String getReportBySuitesIds(@PathVariable("ids") String ids, Model model){
        Report report = reportDAO.getReportBySuiteIDs(ids);
        model.addAttribute("report", report);
        model.addAttribute("passrate", getPassRate(report));
        return "vladislav/report";
    }

    private String getPassRate(Report report){
        double passed = report.getPassedTests().size();
        double total = report.getAllTests().size();
        return String.format("%.2f", passed / total * 100);
    }
}
