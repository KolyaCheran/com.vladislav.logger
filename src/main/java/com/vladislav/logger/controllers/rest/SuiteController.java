package com.vladislav.logger.controllers.rest;

import com.vladislav.logger.dao.SuiteDAO;
import com.vladislav.logger.helpers.Result;
import com.vladislav.logger.models.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suite")
public class SuiteController {

    private final SuiteDAO suiteDAO;

    @Autowired
    public SuiteController(SuiteDAO suiteDAO) {
        this.suiteDAO = suiteDAO;
    }

    @PostMapping("/new")
    public String createNewSuite(@RequestParam("runid") int runId,
                                 @RequestParam("status") String status,
                                 @RequestParam("name") String name,
                                 @RequestParam("starthour") int startHour,
                                 @RequestParam("startminute") int startMinute,
                                 @RequestParam("startsecond") int startSecond){
        Suite suite = new Suite();
        suite.setName(name);
        suite.setRunId(runId);
        suite.setStatus(status);
        suite.setStartHour(startHour);
        suite.setStartMinute(startMinute);
        suite.setStartSecond(startSecond);
        int suiteId = suiteDAO.createNewSuite(suite);
        return "{\"id\": " + suiteId +" }";
    }

    @PatchMapping("/{id}/update")
    public String finishSuite(@PathVariable("id") int id,
                                 @RequestParam("status") String status,
                                 @RequestParam("endhour") int endHour,
                                 @RequestParam("endminute") int endMinute,
                                 @RequestParam("endsecond") int endSecond){
        Suite suite = new Suite();
        suite.setId(id);
        suite.setStatus(status);
        suite.setEndHour(endHour);
        suite.setEndMinute(endMinute);
        suite.setEndSecond(endSecond);
        suiteDAO.finishSuite(suite);
        return "{\"success\": true}";
    }

    @PatchMapping("/{id}/update/result")
    public String updateResult(@PathVariable("id") int stepId,
                               @RequestParam("result") String result){
        Suite suite = new Suite();
        suite.setId(stepId);
        suite.setResult(Result.getResultByText(result));
        suiteDAO.updateSuiteResult(suite);
        return "{\"success\": true }";
    }

    @PatchMapping("/{id}/update/status")
    public String updateStatus(@PathVariable("id") int stepId,
                               @RequestParam("status") String status){
        Suite suite = new Suite();
        suite.setId(stepId);
        suite.setStatus(status);
        suiteDAO.updateSuiteStatus(suite);
        return "{\"success\": true }";
    }
}
