package com.empMgtSytm.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeFoundNullException extends RuntimeException {

	public EmployeeFoundNullException(String msg) {
		super(msg);
	}

}
