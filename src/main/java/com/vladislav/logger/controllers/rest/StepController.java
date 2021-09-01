package com.vladislav.logger.controllers.rest;

import com.vladislav.logger.dao.StepDAO;
import com.vladislav.logger.models.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/step")
public class StepController {

    private final StepDAO stepDAO;

    @Autowired
    public StepController(StepDAO stepDAO) {
        this.stepDAO = stepDAO;
    }

    @PostMapping("/new")
    public String createNewTest(@RequestParam("testId") int testId,
                                @RequestParam("message") String message,
                                @RequestParam("result") String result){
        Step step = new Step();
        step.setTestId(testId);
        step.setMessage(message);
        step.setResult(result);
        int stepId = stepDAO.createNewStep(step);
        return "{\"id\": " + stepId +" }";
    }
}
