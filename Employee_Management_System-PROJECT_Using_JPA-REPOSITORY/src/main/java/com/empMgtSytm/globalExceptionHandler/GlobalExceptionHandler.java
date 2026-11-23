package com.empMgtSytm.globalExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.empMgtSytm.customExceptions.DuplicateEmailException;
import com.empMgtSytm.customExceptions.DuplicatePhoneNumberException;
import com.empMgtSytm.customExceptions.EmployeeFoundNullException;
import com.empMgtSytm.customExceptions.EmployeeNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

//  404 - employee id not found
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFound(EmployeeNotFoundException ex) {

        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
//  400 - Bad Request ---> Employee Found Null
    @ExceptionHandler(EmployeeFoundNullException.class)
    public ResponseEntity<String> handleEmployeeFoundNull(EmployeeFoundNullException ex) {

        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

//  409 - email already exists
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmail(DuplicateEmailException ex) {

        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);

    }

//  409 - phone number already exists
    @ExceptionHandler(DuplicatePhoneNumberException.class)
    public ResponseEntity<String> handleDuplicatePhone(DuplicatePhoneNumberException ex) {

        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);

    }

}