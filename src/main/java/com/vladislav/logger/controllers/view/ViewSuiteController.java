package com.vladislav.logger.controllers.view;

import com.vladislav.logger.dao.SuiteDAO;
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

    @Autowired
    public ViewSuiteController(SuiteDAO suiteDAO) {
        this.suiteDAO = suiteDAO;
    }

    @GetMapping("/{ids}")
    public String createNewSuite(@PathVariable("ids") String ids, Model model){
        List<Suite> suites = suiteDAO.getSuites(ids);
        model.addAttribute("suites", suites);
        return "vladislav/suites";
    }
}
