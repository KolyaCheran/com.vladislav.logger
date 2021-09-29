package com.vladislav.logger.models;


import java.util.List;

public class Report {
    private List<Test> allTests;
    private List<Test> passedTests;
    private List<Test> failedTests;
    private List<Test> skippedTests;
    private List<Test> finishedTests;
    private List<Test> notFinishedTests;

    public List<Test> getAllTests() {
        return allTests;
    }

    public void setAllTests(List<Test> allTests) {
        this.allTests = allTests;
    }

    public List<Test> getPassedTests() {
        return passedTests;
    }

    public void setPassedTests(List<Test> passedTests) {
        this.passedTests = passedTests;
    }

    public List<Test> getFailedTests() {
        return failedTests;
    }

    public void setFailedTests(List<Test> failedTests) {
        this.failedTests = failedTests;
    }

    public List<Test> getSkippedTests() {
        return skippedTests;
    }

    public void setSkippedTests(List<Test> skippedTests) {
        this.skippedTests = skippedTests;
    }

    public List<Test> getFinishedTests() {
        return finishedTests;
    }

    public void setFinishedTests(List<Test> finishedTests) {
        this.finishedTests = finishedTests;
    }

    public List<Test> getNotFinishedTests() {
        return notFinishedTests;
    }

    public void setNotFinishedTests(List<Test> notFinishedTests) {
        this.notFinishedTests = notFinishedTests;
    }
}
