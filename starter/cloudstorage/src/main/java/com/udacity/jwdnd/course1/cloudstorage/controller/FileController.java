package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * FileController for
 *  -- uploading file
 *  -- downloading file
 *  -- deleting file
 */
@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    /**
     * Helper Method to get username from fileid
     * @param fileid
     * @return username
     */
    public String getUsernameFromFileid (Integer fileid) {
        Integer userid = fileService.getUseridByFileid(fileid);
        User currentuser = userService.getUserByUserid(userid);
        String currentusername = currentuser.getUsername();
        return currentusername;
    }

    /**
     * Download file by fileid
     * @param fileid
     * @param authentication
     * @param model
     * @return
     * @throws IOException
     */
    @GetMapping("/download/{fileid}")
    @PreAuthorize("@this.getUsernameFromFileid(#fileid).equals(#authentication.getName())")
    public ResponseEntity<byte[]> fileDownloadByFileid (@PathVariable Integer fileid, Authentication authentication, Model model) throws IOException {

        try {
            File targetFile = fileService.getFileByFileid(fileid);
            if (targetFile != null) {
                return ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION)
                        .contentLength(targetFile.getFiledata().length)
                        .contentType(MediaType.valueOf(targetFile.getContenttype()))
                        .body(targetFile.getFiledata());
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    /**
     * Delete file from database
     * @param fileid
     * @param authentication
     * @param model
     * @return
     * @throws IOException
     */
    @GetMapping("/delete/{fileid}")
    @PreAuthorize("@this.getUsernameFromFileid(#fileid).equals(#authentication.getName())")
    public String deleteFile (@PathVariable Integer fileid, Authentication authentication, Model model) throws IOException {
        try {
            model.addAttribute("success", fileService.deleteByFileid(fileid));
        } catch (Exception err) {
            String errmessage = err.getLocalizedMessage();
            model.addAttribute("errorhappens", true);
            model.addAttribute("errormsg", errmessage);
        }
        return "result";
    }

    /**
     * Upload file into database
     * @param fileUpload
     * @param authentication
     * @param model
     * @return
     * @throws IOException
     */
    @PostMapping
    public String uploadFile (@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) throws IOException {

        // set userid for file
        Integer currentuserid = userService.getUserByUsername(authentication.getName()).getUserid();

        Integer addedfileid = fileService.addFile(fileUpload, currentuserid);
        if (addedfileid == -2) {
            model.addAttribute("errorhappens", true);
            model.addAttribute("errormsg", "You haven't selected a file to upload!");
        } else {
            model.addAttribute("success", addedfileid > 0);
        }

        return "result";
    }
}
