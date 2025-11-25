package com.expensetracker.smartexpensetracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Annotate with @ResponseStatus to map this exception directly to an HTTP status code
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

    // Standard constructor that accepts a message
    public UserNotFoundException(String message) {
        super(message);
    }
}
