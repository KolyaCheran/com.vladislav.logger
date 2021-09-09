package com.vladislav.logger.models;

public class Attachment {

    private int id;
    private int actionId;
    private String location;

    public Attachment(int id, String location) {
        this.id = id;
        this.location = location;
    }

    public Attachment() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
