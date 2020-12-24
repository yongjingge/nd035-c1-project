package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private NoteService noteService;
    private CredentialService credentialService;
    private FileService fileService;

    public HomeController(UserService userService, NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    /**
     * getHomePage will show users their homepage with notes, credentials, and files once logged in.
     * @param authentication
     * @param model
     * @return home view
     */
    @GetMapping
    public String getHomePage (Authentication authentication, Model model) {
        // deal with binding
        model.addAttribute("noteForm", new NoteForm());
        model.addAttribute("credentialForm", new CredentialForm());
        model.addAttribute("file", new File());

        String username = authentication.getName();
        Integer userid = userService.getUserByUsername(username).getUserid();
        model.addAttribute("notes", noteService.getNotesByUserid(userid));

        // "credentials" is a list of credentials with ENCRYPTED password, used in the 'Example Credential Password' table.
        model.addAttribute("credentials", credentialService.getCredentialsByUser(userid));

        model.addAttribute("files", fileService.getFilesByUserid(userid));

        return "home";
    }
}
