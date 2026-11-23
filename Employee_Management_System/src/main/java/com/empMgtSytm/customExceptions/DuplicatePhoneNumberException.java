package com.empMgtSytm.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatePhoneNumberException extends RuntimeException {

	public DuplicatePhoneNumberException(String msg) {
		super(msg);
	}

}
