package com.vladislav.logger.scheduled;

import org.apache.tomcat.util.http.fileupload.FileUtils;
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


    @Scheduled(cron = "0 0 0/1 * * *")
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
        int countOfDays = 2;
        List<String> days = new ArrayList<>();
        for (int i = 0; i < countOfDays; i++){
            LocalDateTime ldt = LocalDateTime.now().minusDays(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            String formatDateTime = ldt.format(formatter);
            days.add(formatDateTime);
        }
        return !days.contains(directory);
    }
}
