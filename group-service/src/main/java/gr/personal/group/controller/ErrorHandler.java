package gr.personal.group.controller;

import gr.personal.group.domain.GenericJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Nick Kanakis on 9/5/2017.
 */

@ControllerAdvice
public class ErrorHandler {

    Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    // TODO: Replace general handler with specific error handlers

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GenericJson parseError(Exception e){
        logger.error("Returning HTTP 400 Bad Request", e);
        return new GenericJson("Error", "Something went terrible wrong please try again!",true);
    }
}