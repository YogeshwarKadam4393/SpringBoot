package com.empMgtSytm.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateDepartmentEntryFoundException extends RuntimeException {

	public DuplicateDepartmentEntryFoundException(String msg) {
		super(msg);
	}

}
