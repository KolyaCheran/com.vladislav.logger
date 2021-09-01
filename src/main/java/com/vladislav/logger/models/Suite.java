package com.vladislav.logger.models;

public class Suite {

    private int id;
    private int runId;
    private String name;
    private String suiteInfo;
    private String status;
    private String result;
    private int startMinute;
    private int startHour;
    private int startSecond;
    private int endMinute;
    private int endSecond;
    private int endHour;

    public Suite(int id, int runId, String name, String suiteInfo, String status, String result, int startMinute, int startHour, int startSecond, int endMinute, int endSecond, int endHour, int endMonth, int endYear) {
        this.id = id;
        this.runId = runId;
        this.name = name;
        this.suiteInfo = suiteInfo;
        this.status = status;
        this.result = result;
        this.startMinute = startMinute;
        this.startHour = startHour;
        this.startSecond = startSecond;
        this.endMinute = endMinute;
        this.endSecond = endSecond;
        this.endHour = endHour;
        this.endMonth = endMonth;
        this.endYear = endYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartSecond() {
        return startSecond;
    }

    public void setStartSecond(int startSecond) {
        this.startSecond = startSecond;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public int getEndSecond() {
        return endSecond;
    }

    public void setEndSecond(int endSecond) {
        this.endSecond = endSecond;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public Suite() {

    }

    private int endMonth;
    private int endYear;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }

    public String getSuiteInfo() {
        return suiteInfo;
    }

    public void setSuiteInfo(String suiteInfo) {
        this.suiteInfo = suiteInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
}
