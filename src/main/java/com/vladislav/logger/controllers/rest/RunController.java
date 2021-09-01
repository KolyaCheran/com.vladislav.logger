package com.vladislav.logger.controllers.rest;

import com.vladislav.logger.dao.RunDAO;
import com.vladislav.logger.models.Run;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/run")
public class RunController {

    private final RunDAO runDAO;

    @Autowired
    public RunController(RunDAO runDAO){
        this.runDAO = runDAO;
    }

   /* @GetMapping
    public String test(){
        return "all works good!";
    }*/

    @PostMapping("/new")
    public String createNewRun(@RequestParam("build") String build,
                               @RequestParam("day") int day,
                               @RequestParam("month") int month,
                               @RequestParam("year") int year){
        Run run = new Run();
        run.setBuild(build);
        run.setDay(day);
        run.setMonth(month);
        run.setYear(year);
        int runId = runDAO.createRun(run);
        return "{\"id\": " + runId +" }";
    }
}
