package com.vladislav.logger.controllers.rest;

import com.vladislav.logger.dao.AttachmentDAO;
import com.vladislav.logger.models.Attachment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    private final AttachmentDAO attachmentDAO;

    public AttachmentController(AttachmentDAO attachmentDAO){
        this.attachmentDAO = attachmentDAO;
    }


    @PostMapping( "/image")
    public String createNewImgAttach(@RequestParam("image") MultipartFile file,
                                @RequestParam("actionid") int actionId){
        String location = getFileLocationByDate();
        Attachment attachment = new Attachment();
        attachment.setActionId(actionId);
        attachment.setLocation(location);
        attachment.setId(attachmentDAO.createNewAttachment(attachment));

        File newFile = new File(location + File.separator + attachment.getId() + ".jpeg");
        newFile.getParentFile().mkdirs();
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"success\": true }";
    }

    private String getFileLocationByDate(){
        LocalDateTime ldt = LocalDateTime.now();
        return System.getProperty("user.home") +
              File.separator + "screens" + File.separator + ldt.getDayOfMonth() + "." + ldt.getMonthValue() + "." + ldt.getYear();
    }
}
