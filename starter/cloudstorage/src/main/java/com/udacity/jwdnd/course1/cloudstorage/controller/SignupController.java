package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * finished
 */

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignupPage () {
        return "signup";
    }

    @PostMapping
    public String addSignup (User user, Model model) {
        String signupError = null;

        if (! userService.isUsernameValid(user.getUsername())) {
            signupError = "The username already exists in our system.";
        }

        if (signupError == null) {
            int keyGenerated = userService.createUser(user);
            if (keyGenerated < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            // will be added into login page as well
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
            return "signup";
        }

        return "login";
    }
}
