package com.vladislav.logger.models;

import com.vladislav.logger.helpers.Result;

import java.util.List;

public class Step {

    private int id;
    private int testId;
    private Result result;
    private String message;
    private List<Action> actions;

    public Step(int id, String result, String message) {
        this.id = id;
        this.result = Result.getResultByText(result);
        this.message = message;
    }

    public Step() {

    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getResult() {
        return result.getResultText();
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
