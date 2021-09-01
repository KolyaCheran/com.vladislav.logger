package com.vladislav.logger.controllers.rest;

import com.vladislav.logger.dao.ActionDAO;
import com.vladislav.logger.models.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action")
public class ActionController {

    private final ActionDAO actionDAO;

    @Autowired
    public ActionController(ActionDAO actionDAO) {
        this.actionDAO = actionDAO;
    }

    @PostMapping("/new")
    public String createNewTest(@RequestParam("stepid") int stepId,
                                @RequestParam("message") String message,
                                @RequestParam("result") String result,
                                @RequestParam("actionhour") int actionHour,
                                @RequestParam("actionminute") int actionMinute,
                                @RequestParam("actionsecond") int actionSecond){
        Action action = new Action();
        action.setStepId(stepId);
        action.setMessage(message);
        action.setResult(result);
        action.setActionHour(actionHour);
        action.setActionMinute(actionMinute);
        action.setActionSecond(actionSecond);
        int actionId = actionDAO.createNewAction(action);
        return "{\"id\": " + actionId +" }";
    }
}
