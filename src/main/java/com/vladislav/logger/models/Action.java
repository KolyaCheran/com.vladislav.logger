package com.vladislav.logger.models;

import java.util.List;

public class Action {

    private int id;
    private int stepId;
    private String message;
    private String error;
    private String result;
    private int actionHour;
    private int actionMinute;
    private int actionSecond;
    private List<Attachment> attachments;

    public Action(int id, String message, String error, String result, int actionHour, int actionMinute, int actionSecond) {
        this.id = id;
        this.message = message;
        this.error = error;
        this.result = result;
        this.actionHour = actionHour;
        this.actionMinute = actionMinute;
        this.actionSecond = actionSecond;
    }

    public Action() {

    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

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
        if(message.length() > 1000){
            System.out.println("Message is longer than 1000 chars. Extra chars removed");
            this.message = message.substring(0, 1000);
            return;
        }
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        if(error.length() > 10000){
            System.out.println("Error is longer than 10000 chars. Extra chars removed");
            this.error = error.substring(0, 10000);
            return;
        }
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
