package com.vladislav.logger.helpers;

public enum Result {

    NOT_RUN(0, "not run"),
    RUN(1, "run"),
    STOP(2, "stop"),
    DEBUG(3, "debug"),
    INFO(4, "debug"),
    PASSED(5, "passed"),
    WARNING(6, "warning"),
    SKIP(7, "skipped"),
    FAILED(8, "failed"),
    ERROR(9, "error");

    private int resultIndex;
    private String resultText;

    Result(int resultIndex, String resultText){
        this.resultIndex = resultIndex;
        this.resultText = resultText;
    }

    public int getResultIndex(){
        return resultIndex;
    }

    public String getResultText(){
        return resultText;
    }

    public static Result getResultByText (String resultText){
        Result resultToReturn = null;
        for (Result result: Result.values()){
            if (result.getResultText().equals(resultText)){
                resultToReturn = result;
            }
        }
        return resultToReturn;
    }
}

