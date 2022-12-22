package com.vladislav.logger.controllers.rest;

import com.vladislav.logger.dao.StepDAO;
import com.vladislav.logger.models.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/step")
public class StepController {

    private final StepDAO stepDAO;

    @Autowired
    public StepController(StepDAO stepDAO) {
        this.stepDAO = stepDAO;
    }

    @PostMapping("/new")
    public String createNewStep(@RequestParam("testid") int testId,
                                @RequestParam("message") String message,
                                @RequestParam("result") String result){
        Step step = new Step();
        step.setTestId(testId);
        step.setMessage(message);
        step.setResult(result);
        int stepId = stepDAO.createNewStep(step);
        return "{\"id\": " + stepId +" }";
    }

    @PatchMapping("/{id}/update/result")
    public String updateResult(@PathVariable("id") int stepId,
                                @RequestParam("result") String result){
        Step step = new Step();
        step.setId(stepId);
        step.setResult(result);
        stepDAO.updateStepResult(step);
        return "{\"success\": true }";
    }
}
