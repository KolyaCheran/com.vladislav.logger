package com.vladislav.logger.controllers.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping()
    public String homePage(){
        return "vladislav/home";
    }
}
