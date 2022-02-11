package com.vladislav.logger.scheduled;

import com.vladislav.logger.dao.*;
import com.vladislav.logger.helpers.TimeHelper;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final int DAYS_FOR_ATTACHMENTS = 2;
    private static final int DAYS_FOR_DB_ROWS = 5;
    private final ActionDAO actionDAO;
    private final AttachmentDAO attachmentDAO;
    private final RunDAO runDAO;
    private final StepDAO stepDAO;
    private final SuiteDAO suiteDAO;
    private final TestDAO testDAO;


    @Autowired
    public ScheduledTasks(ActionDAO actionDAO, AttachmentDAO attachmentDAO, RunDAO runDAO, StepDAO stepDAO, SuiteDAO suiteDAO, TestDAO testDAO) {
        this.actionDAO = actionDAO;
        this.attachmentDAO = attachmentDAO;
        this.runDAO = runDAO;
        this.stepDAO = stepDAO;
        this.suiteDAO = suiteDAO;
        this.testDAO = testDAO;
    }

    @Scheduled(cron = "0 0 */1 * * *")
    private void deleteOldAttachments(){
        System.out.println("Try to remove old attachments");
        String location = System.getProperty("user.home") + "\\screens\\";
        File file = new File(location);
        String [] directories = file.list();
        if(directories == null){
            return;
        }
        for (String directory : directories) {
            if(isOldDirectory(directory)) {
                try {
                    FileUtils.deleteDirectory(new File(location + directory));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isOldDirectory(String directory){
        List<String> days = new ArrayList<>();
        for (int i = 0; i < DAYS_FOR_ATTACHMENTS; i++){
            LocalDateTime ldt = LocalDateTime.now().minusDays(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            String formatDateTime = ldt.format(formatter);
            days.add(formatDateTime);
        }
        return !days.contains(directory);
    }

    //@Scheduled(cron = "0 0 */1 * * *")
    private void deleteOldRowsFromDB(){
        System.out.println("Try to remove old DB rows");
        removeOldActions();
        removeOldAttachments();
        removeOldRuns();
        removeOldSteps();
        removeOldSuites();
        removeOldTests();
    }

    private void removeOldActions(){
        actionDAO.removeOldActions((int)TimeHelper.getUnixTimeStampMinusDays(DAYS_FOR_DB_ROWS));
    }

    private void removeOldAttachments(){
        attachmentDAO.removeOldAttachments((int)TimeHelper.getUnixTimeStampMinusDays(DAYS_FOR_DB_ROWS));
    }

    private void removeOldRuns(){
        runDAO.removeOldRuns((int)TimeHelper.getUnixTimeStampMinusDays(DAYS_FOR_DB_ROWS));
    }

    private void removeOldSteps(){
        stepDAO.removeOldSteps((int)TimeHelper.getUnixTimeStampMinusDays(DAYS_FOR_DB_ROWS));
    }

    private void removeOldSuites(){
        suiteDAO.removeOldSuites((int)TimeHelper.getUnixTimeStampMinusDays(DAYS_FOR_DB_ROWS));
    }
    private void removeOldTests(){
        testDAO.removeOldTests((int)TimeHelper.getUnixTimeStampMinusDays(DAYS_FOR_DB_ROWS));
    }
}
