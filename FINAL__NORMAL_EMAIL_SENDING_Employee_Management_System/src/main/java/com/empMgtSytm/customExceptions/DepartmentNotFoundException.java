package com.empMgtSytm.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DepartmentNotFoundException extends RuntimeException {

	public DepartmentNotFoundException(String msg) {
		super(msg);
	}

}
