package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * NoteController for
 *  -- adding note
 *  -- editing note
 *  -- deleting note (/note/delete/${note.noteid}
 */
@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    /**
     * Helper Method to get username from noteid
     * used for @ProAuthorize
     * @param noteid
     * @return username
     */
    public String getUsernameFromNoteid (Integer noteid) {
        Integer userid = noteService.getUseridByNoteid(noteid);
        User currentuser = userService.getUserByUserid(userid);
        String currentusername = currentuser.getUsername();
        return currentusername;
    }

    /**
     * Delete note from database
     * @param noteid
     * @param authentication
     * @param model
     * @return
     * @throws IOException
     */
    @GetMapping("/delete/{noteid}")
    @PreAuthorize("@this.getUsernameFromNoteid(#noteid).equals(#authentication.getName())")
    public String deleteNote (@PathVariable Integer noteid, Authentication authentication, Model model) throws IOException {
        try {
            model.addAttribute("success", noteService.deleteByNoteid(noteid));
        } catch (Exception err) {
            String errmessage = err.getLocalizedMessage();
            model.addAttribute("errorhappens", true);
            model.addAttribute("errormsg", errmessage);
        }
        return "result";
    }

    /**
     * Edit note
     * @param noteForm
     * @param authentication
     * @param model
     * @return
     * @throws IOException
     * can not have @PostMapping at same path!
     */
    @PreAuthorize("@this.getUsernameFromNoteid(#noteForm.getNoteid()).equals(#authentication.getName())")
    public String updateNote (@ModelAttribute("noteForm") NoteForm noteForm, Authentication authentication, Model model) throws IOException {
        try {
            noteService.updateNote(noteForm);
            model.addAttribute("success", true);
//            if (noteService.getNoteByNoteid(noteForm.getNoteid()) != null) {
//                noteService.updateNote(noteForm);
//                model.addAttribute("success", true);
//            } else {
//                model.addAttribute("success", false);
//            }
        } catch (Exception err) {
            String errmessage = err.getLocalizedMessage();
            model.addAttribute("errorhappens", true);
            model.addAttribute("errormsg", errmessage);
        }
        return "result";
    }

    /**
     * Add note into database (if note exists, turn to edit it)
     * @param noteForm
     * @param authentication
     * @param model
     * @return
     * @throws IOException
     */
    @PostMapping
    public String addNote (@ModelAttribute("noteForm") NoteForm noteForm, Authentication authentication, Model model) throws IOException {
        // if note already exists, we go to edit it.
        if (noteService.getNoteByNoteid(noteForm.getNoteid()) != null) {
            updateNote(noteForm, authentication, model);
        } else {
            // set userid for noteForm
            Integer currentuserid = userService.getUserByUsername(authentication.getName()).getUserid();
            noteForm.setUserid(currentuserid);

            try {
                Integer addednoteid = noteService.addNote(noteForm);
                model.addAttribute("success", addednoteid > 0);
            } catch (Exception err) {
                String errmessage = err.getLocalizedMessage();
                model.addAttribute("errorhappens", true);
                model.addAttribute("errormsg", errmessage);
            }
        }
        return "result";
    }
}
