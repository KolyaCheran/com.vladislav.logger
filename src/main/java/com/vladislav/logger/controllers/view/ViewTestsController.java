package com.vladislav.logger.controllers.view;

import com.vladislav.logger.dao.ReportDAO;
import com.vladislav.logger.dao.TestDAO;
import com.vladislav.logger.models.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/tests")
public class ViewTestsController {

    private final TestDAO testDAO;
    private final ReportDAO reportDAO;

    @Autowired
    public ViewTestsController(TestDAO testDAO, ReportDAO reportDAO) {
        this.testDAO = testDAO;
        this.reportDAO = reportDAO;
    }

    @GetMapping("/{id}")
    public String getTests(@PathVariable("id") int suiteId, Model model){
        List<Test> tests = testDAO.getTests(suiteId);
        model.addAttribute("tests", tests);
        return "vladislav/tests";
    }

    @GetMapping("/failed/{id}")
    public String getFailedTests(@PathVariable("id") String suiteIds, Model model){
        List<Test> tests = reportDAO.getFailedTests(suiteIds);
        model.addAttribute("tests", tests);
        return "vladislav/tests";
    }

    @GetMapping("/skipped/{id}")
    public String getSkippedTests(@PathVariable("id") String suiteIds, Model model){
        List<Test> tests = reportDAO.getSkippedTests(suiteIds);
        model.addAttribute("tests", tests);
        return "vladislav/tests";
    }
}
