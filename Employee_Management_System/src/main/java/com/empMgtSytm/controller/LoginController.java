package com.empMgtSytm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empMgtSytm.dto.LoginRequestDTO;
import com.empMgtSytm.dto.LoginResponseDTO;
import com.empMgtSytm.service.LoginService;

@RestController
@RequestMapping("/admin")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

		LoginResponseDTO response = loginService.login(loginRequestDTO);

		if (response.isSuccess()) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
}