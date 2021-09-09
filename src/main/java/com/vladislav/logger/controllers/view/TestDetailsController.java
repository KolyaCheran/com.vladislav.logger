package com.vladislav.logger.controllers.view;

import com.vladislav.logger.dao.TestDAO;
import com.vladislav.logger.models.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test_details")
public class TestDetailsController {

    private final TestDAO testDAO;

    @Autowired
    public TestDetailsController(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    @GetMapping("/{id}")
    public String getTestDetails(@PathVariable("id") int id, Model model){
        Test test = testDAO.getTest(id);
        model.addAttribute("currentTest", test);
        return "vladislav/testDetails";
    }
}
