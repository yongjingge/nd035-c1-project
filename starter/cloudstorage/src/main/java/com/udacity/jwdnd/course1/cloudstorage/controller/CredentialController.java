package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * CredentialController for
 * -- adding credential
 * -- editing credential
 * -- deleting credential (/credential/delete/${credential.credentialid}
 */

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    /**
     * Helper Method to get username from credentialid
     * used for @ProAuthorize
     * @param credentialid
     * @return username
     */
    public String getUsernameFromCredentialid (Integer credentialid) {
        Integer userid = credentialService.getUseridByCredentialid(credentialid);
        User currentuser = userService.getUserByUserid(userid);
        String currentusername = currentuser.getUsername();
        return currentusername;
    }

    /**
     * Delete credential from database
     * @param credentialid
     * @param authentication
     * @param model
     * @return
     * @throws IOException
     */
    @GetMapping("/delete/{credentialid}")
    @PreAuthorize("@this.getUsernameFromCredentialid(#credentialid).equals(#authentication.getName())")
    public String deleteCredential (@PathVariable Integer credentialid, Authentication authentication, Model model) throws IOException {
        try {
            model.addAttribute("success", credentialService.deleteByCredentialid(credentialid));
        } catch (Exception err) {
            String errmessage = err.getLocalizedMessage();
            model.addAttribute("errorhappens", true);
            model.addAttribute("errormsg", errmessage);
        }
        return "result";
    }

    /**
     * Edit credential
     * @param credentialForm
     * @param authentication
     * @param model
     * @return
     * @throws IOException
     * can not have @PostMapping at same path!
     */
    @PreAuthorize("@this.getUsernameFromCredentialid(#credentialForm.getCredentialid()).equals(#authentication.getName())")
    public String updateCredential (@ModelAttribute("credentialForm") CredentialForm credentialForm, Authentication authentication, Model model) throws IOException {
        try {
            credentialService.updateCredential(credentialForm);
            model.addAttribute("success", true);
//            if (credentialService.getCredential(credentialForm.getCredentialid()) != null) {
//                credentialService.updateCredential(credentialForm);
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
     * Add credential into database (if credential exists, turn to edit it)
     * @param credentialForm
     * @param authentication
     * @param model
     * @return
     * @throws IOException
     */
    @PostMapping
    public String addCredential (@ModelAttribute("credentialForm") CredentialForm credentialForm, Authentication authentication, Model model) throws IOException {
        // if credential already exists, we go to edit it.
        if (credentialService.getCredential(credentialForm.getCredentialid()) != null) {
            updateCredential(credentialForm, authentication, model);
        } else {
            // set userid for credentialForm
            Integer currentuserid = userService.getUserByUsername(authentication.getName()).getUserid();
            credentialForm.setUserid(currentuserid);

            try {
                Integer addedcredentialid = credentialService.addCredential(credentialForm);
                model.addAttribute("success", addedcredentialid > 0 ? true : false);
            } catch (Exception err) {
                String errmessage = err.getLocalizedMessage();
                model.addAttribute("errorhappens", true);
                model.addAttribute("errormsg", errmessage);
            }
        }
        return "result";
    }
}
