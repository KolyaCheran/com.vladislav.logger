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
    public String getSuites(@PathVariable("ids") String ids, Model model){
        List<Suite> suites = suiteDAO.getSuites(ids);
        model.addAttribute("suiteIds", getIdsFromSuites(suites));
        model.addAttribute("suites", suites);
        return "vladislav/suites";
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
