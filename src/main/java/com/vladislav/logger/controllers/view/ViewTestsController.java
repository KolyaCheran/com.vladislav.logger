package com.vladislav.logger.controllers.view;

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

    @Autowired
    public ViewTestsController(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    @GetMapping("/{id}")
    public String getTests(@PathVariable("id") int id, Model model){
        List<Test> tests = testDAO.getTest(id);
        model.addAttribute("tests", tests);
        return "vladislav/tests";
    }
}
