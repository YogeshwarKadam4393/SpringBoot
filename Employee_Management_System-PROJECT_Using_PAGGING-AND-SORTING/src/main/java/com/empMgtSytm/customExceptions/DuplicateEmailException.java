package com.empMgtSytm.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// @ResponseStatus(HttpStatus.CONFLICT) tells Spring: when this exception is thrown, 
// send back HTTP status 409 instead of the default 500.

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEmailException extends RuntimeException {
	
    public DuplicateEmailException(String message) {
        super(message);
    }
    
}
