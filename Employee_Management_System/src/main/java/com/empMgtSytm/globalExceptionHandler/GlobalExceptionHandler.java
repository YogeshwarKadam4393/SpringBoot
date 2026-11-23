package com.empMgtSytm.globalExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.empMgtSytm.customExceptions.DepartmentFoundNullException;
import com.empMgtSytm.customExceptions.DepartmentNotFoundException;
import com.empMgtSytm.customExceptions.DuplicateDepartmentEntryFoundException;
import com.empMgtSytm.customExceptions.DuplicateEmailException;
import com.empMgtSytm.customExceptions.DuplicatePhoneNumberException;
import com.empMgtSytm.customExceptions.EmployeeFoundNullException;
import com.empMgtSytm.customExceptions.EmployeeNotFoundException;
import com.empMgtSytm.dto.APIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handles validation exceptions globally when request data fails validation.
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

		// Creates a Map to store field names and their corresponding validation error messages.
		// Example: "email" -> "Please enter a valid email address"
		Map<String, String> error = new HashMap<String, String>();

		// Retrieves all field-level validation errors from the BindingResult.
		// Each FieldError contains details about a field that failed validation.
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

		// Iterates through each validation error to extract the field name and error message.
		for (FieldError fieldError : fieldErrors) {

			// Stores the field name as the key and its validation message as the value.
			error.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		// Returns the validation errors in the response body with HTTP status 400 (BadRequest).
		return new ResponseEntity<Map<String, String>>(error, HttpStatus.BAD_REQUEST);
	}
	

//  404 - employee id not found
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity handleEmployeeNotFound(EmployeeNotFoundException ex) {

		APIResponse response = new APIResponse();

		response.setErrorMsg(ex.getMessage());
		response.setErrorCode(HttpStatus.NOT_FOUND.value());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
	}

//  400 - Bad Request ---> Employee Found Null
	@ExceptionHandler(EmployeeFoundNullException.class)
	public ResponseEntity<?> handleEmployeeFoundNull(EmployeeFoundNullException ex) {

		APIResponse response = new APIResponse();

		response.setErrorMsg(ex.getMessage());
		response.setErrorCode(HttpStatus.BAD_REQUEST.value());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

//  409 - email already exists
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<?> handleDuplicateEmail(DuplicateEmailException ex) {

		APIResponse response = new APIResponse();

		response.setErrorMsg(ex.getMessage());
		response.setErrorCode(HttpStatus.CONFLICT.value());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity(response, HttpStatus.CONFLICT);

	}

//  409 - duplicate Department Entry found
	@ExceptionHandler(DuplicateDepartmentEntryFoundException.class)
	public ResponseEntity<?> handleDuplicateDepartmentEntryFoundException(DuplicateDepartmentEntryFoundException ex) {

		APIResponse response = new APIResponse();

		response.setErrorMsg(ex.getMessage());
		response.setErrorCode(HttpStatus.CONFLICT.value());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity(response, HttpStatus.CONFLICT);

	}

//  409 - phone number already exists
	@ExceptionHandler(DuplicatePhoneNumberException.class)
	public ResponseEntity<?> handleDuplicatePhone(DuplicatePhoneNumberException ex) {

		APIResponse response = new APIResponse();

		response.setErrorMsg(ex.getMessage());
		response.setErrorCode(HttpStatus.CONFLICT.value());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity(response, HttpStatus.CONFLICT);

	}

//  404 - Department id not found
	@ExceptionHandler(DepartmentNotFoundException.class)
	public ResponseEntity<?> handleDepartmentNotFound(DepartmentNotFoundException ex) {

		APIResponse response = new APIResponse();

		response.setErrorMsg(ex.getMessage());
		response.setErrorCode(HttpStatus.NOT_FOUND.value());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
	}

//  404 - Department id not found Null
	@ExceptionHandler(DepartmentFoundNullException.class)
	public ResponseEntity<?> handleDepartmentFoundNull(DepartmentFoundNullException ex) {

		APIResponse response = new APIResponse();

		response.setErrorMsg(ex.getMessage());
		response.setErrorCode(HttpStatus.NOT_FOUND.value());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
	}

}