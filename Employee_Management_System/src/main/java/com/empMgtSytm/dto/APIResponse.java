package com.empMgtSytm.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class APIResponse {
	
	private String errorMsg;
	
	private int errorCode;

	private LocalDateTime timestamp;

}
