package gr.personal.user.controller;

import gr.personal.user.domain.GenericJson;
import gr.personal.user.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Nick Kanakis on 9/5/2017.
 */

@ControllerAdvice
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
    @Value("${error.generic}")
    private String GENERIC_ERROR;

    // TODO: Replace general handler with specific error handlers
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GenericJson parseError(Exception e){
        logger.error("Returning HTTP 400 Bad Request", e);
        return new GenericJson(Constants.NOK, GENERIC_ERROR,true);
    }
}
