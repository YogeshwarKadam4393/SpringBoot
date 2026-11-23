package com.empMgtSytm.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.empMgtSytm.dto.LoginRequestDTO;
import com.empMgtSytm.dto.LoginResponseDTO;
import com.empMgtSytm.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

	@Value("${app.admin.username}")
	private String adminUsername;

	@Value("${app.admin.password}")
	private String adminPassword;

	@Override
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

		log.info("Admin login request received");

		if (loginRequestDTO == null) {
			log.warn("Login request is null");

			return new LoginResponseDTO("Login request cannot be null", false);
		}

		if (adminUsername.equals(loginRequestDTO.getUsername())
				&& adminPassword.equals(loginRequestDTO.getPassword())) {

			log.info("Admin login successful");

			return new LoginResponseDTO("Admin Login Successful", true);
		}

		log.warn("Invalid admin login credentials");

		return new LoginResponseDTO("Invalid Username or Password", false);
	}
}