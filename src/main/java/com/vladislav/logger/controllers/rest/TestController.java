package com.vladislav.logger.controllers.rest;

import com.vladislav.logger.dao.TestDAO;
import com.vladislav.logger.helpers.Result;
import com.vladislav.logger.models.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TestDAO testDAO;

    @Autowired
    public TestController(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    @PostMapping("/new")
    public String createNewTest(@RequestParam("name") String name,
                                 @RequestParam("suiteid") int suiteId,
                                 @RequestParam("status") String status,
                                 @RequestParam("result") String result){
        Test test = new Test();
        test.setName(name);
        test.setSuiteId(suiteId);
        test.setResult(Result.getResultByText(result));
        test.setStatus(status);
        int testId = testDAO.createNewTest(test);
        return "{\"id\": " + testId +" }";
    }

    @PatchMapping("/{id}/update/onstart")
    public String updateTestOnStart(@PathVariable("id") int id,
                                @RequestParam("starthour") int startHour,
                                @RequestParam("startminute") int startMinute,
                                @RequestParam("startsecond") int startSecond,
                                @RequestParam("result") String result,
                                @RequestParam("status") String status){
        Test test = new Test();
        test.setId(id);
        test.setStartHour(startHour);
        test.setStartMinute(startMinute);
        test.setStartSecond(startSecond);
        test.setResult(Result.getResultByText(result));
        test.setStatus(status);
        testDAO.updateTestOnStart(test);
        return "{\"success\": true}";
    }

    @PatchMapping("/{id}/update/result")
    public String updateTestResult(@PathVariable("id") int id,
                                    @RequestParam("result") String result){
        Test test = new Test();
        test.setId(id);
        test.setResult(Result.getResultByText(result));
        testDAO.updateTestResult(test);
        return "{\"success\": true}";
    }

    @PatchMapping("/{id}/update/onfinish")
    public String updateTestOnFinish(@PathVariable("id") int id,
                                    @RequestParam("endhour") int endHour,
                                    @RequestParam("endminute") int endMinute,
                                    @RequestParam("endsecond") int endSecond,
                                    @RequestParam("result") String result,
                                    @RequestParam("status") String status){
        Test test = new Test();
        test.setId(id);
        test.setEndHour(endHour);
        test.setEndMinute(endMinute);
        test.setEndSecond(endSecond);
        test.setResult(Result.getResultByText(result));
        test.setStatus(status);
        testDAO.updateTestOnFinish(test);
        return "{\"success\": true}";
    }
}
