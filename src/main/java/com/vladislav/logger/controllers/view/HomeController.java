package com.vladislav.logger.controllers.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class HomeController {

    @GetMapping()
    public String homePage(Model model){
        LocalDateTime ldt = LocalDateTime.now();
        model.addAttribute("day", ldt.getDayOfMonth());
        model.addAttribute("month", ldt.getMonthValue());
        model.addAttribute("year", ldt.getYear());
        return "vladislav/home";
    }
}
