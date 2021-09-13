package com.vladislav.logger.controllers.view;

import com.vladislav.logger.dao.AttachmentDAO;
import com.vladislav.logger.models.Attachment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/attachment")
public class ViewAttachmentController {

    private final AttachmentDAO attachmentDAO;

    public ViewAttachmentController(AttachmentDAO attachmentDAO){
        this.attachmentDAO = attachmentDAO;
    }

    @GetMapping(value = "/image/page/{id}")
    public String getImage(@PathVariable("id") int id, Model model) {
        model.addAttribute("screenId", id);
        return "vladislav/screen";
    }

    @GetMapping(value = "/image/{id}")
    public @ResponseBody byte[] getImage(@PathVariable("id") String id) {
        Attachment attachment = attachmentDAO.getAttachment(Integer.valueOf(id.replaceAll("\\.png", "")));
        return extractBytes(attachment.getLocation() + "\\" + attachment.getId() + ".png");
    }

    public byte[] extractBytes(String ImageName) {
        byte[] imageInByte = null;
        try {
            File file = new File(ImageName);
            BufferedImage originalImage = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", baos);
            imageInByte = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (imageInByte);
    }
}
