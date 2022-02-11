package com.vladislav.logger.helpers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TimeHelper {

    public static long getUnixTimeStamp(){
        return Instant.now().getEpochSecond();
    }

    public static long getUnixTimeStampMinusDays(int minusDays){
        return Instant.now().minus(minusDays, ChronoUnit.DAYS).getEpochSecond();
    }
}
