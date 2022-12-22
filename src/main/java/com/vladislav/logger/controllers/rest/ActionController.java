package com.vladislav.logger.controllers.rest;

import com.vladislav.logger.dao.ActionDAO;
import com.vladislav.logger.models.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/action")
public class ActionController {

    private final ActionDAO actionDAO;

    @Autowired
    public ActionController(ActionDAO actionDAO) {
        this.actionDAO = actionDAO;
    }

    @PostMapping("/new")
    public String createNewAction(@RequestParam("stepid") int stepId,
                                  @RequestParam("message") String message,
                                  @RequestParam("result") String result,
                                  @RequestParam("actionhour") int actionHour,
                                  @RequestParam("actionminute") int actionMinute,
                                  @RequestParam("actionsecond") int actionSecond) {
        Action action = new Action();
        action.setStepId(stepId);
        action.setMessage(message);
        action.setResult(result);
        action.setActionHour(actionHour);
        action.setActionMinute(actionMinute);
        action.setActionSecond(actionSecond);
        int actionId = actionDAO.createNewAction(action);
        return "{\"id\": " + actionId + " }";
    }

    @PatchMapping("/{id}/update/error")
    public String updateActionWithError(@PathVariable("id") int id,
                                @RequestParam("error") String error,
                                @RequestParam("result") String result) {
        Action action = new Action();
        action.setId(id);
        action.setError(error);
        action.setResult(result);
        actionDAO.updateActionError(action);
        return "{\"success\": true }";
    }
}
