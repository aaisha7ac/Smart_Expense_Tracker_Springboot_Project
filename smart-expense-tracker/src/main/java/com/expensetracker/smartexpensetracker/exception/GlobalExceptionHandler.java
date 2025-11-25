package com.expensetracker.smartexpensetracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// @ControllerAdvice makes this class listen for exceptions across all controllers
@ControllerAdvice
public class GlobalExceptionHandler {

    // This method specifically handles the UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Ensures the 404 status is set
    @ResponseBody // Ensures the return value is written directly to the response body
    public ResponseEntity<String>handlesUserNotFoundException(UserNotFoundException ex) {
        // This returns a clean 404 response with your custom message as the body content.
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

}
