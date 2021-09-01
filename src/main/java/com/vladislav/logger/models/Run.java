package com.vladislav.logger.models;

public class Run {

    private int id;
    private String build;
    private int day;
    private int month;
    private int year;

    public Run(int id, String build, int day, int month, int year) {
        this.id = id;
        this.build = build;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Run(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
