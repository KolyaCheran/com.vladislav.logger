package com.vladislav.logger.models;

public class Action {

    private int id;
    private int stepId;
    private String message;
    private String error;
    private String result;
    private int actionHour;
    private int actionMinute;
    private int actionSecond;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getActionHour() {
        return actionHour;
    }

    public void setActionHour(int actionHour) {
        this.actionHour = actionHour;
    }

    public int getActionMinute() {
        return actionMinute;
    }

    public void setActionMinute(int actionMinute) {
        this.actionMinute = actionMinute;
    }

    public int getActionSecond() {
        return actionSecond;
    }

    public void setActionSecond(int actionSecond) {
        this.actionSecond = actionSecond;
    }
}
