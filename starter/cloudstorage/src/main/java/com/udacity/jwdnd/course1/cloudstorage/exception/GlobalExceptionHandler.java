package com.udacity.jwdnd.course1.cloudstorage.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException (MaxUploadSizeExceededException e, Model model) {
        model.addAttribute("errorhappens", true);
        model.addAttribute("errormsg", "File size exceeds limit!");
        return "result";
    }

    @ExceptionHandler(MultipartException.class)
    public String handleMultipartException (MultipartException e, Model model) {
        model.addAttribute("errorhappens", true);
        model.addAttribute("errormsg", e.getLocalizedMessage());
        return "result";
    }

}
