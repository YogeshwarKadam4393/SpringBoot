package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmployeeService {

	//Logger log = LoggerFactory.getLogger(EmployeeService.class);

	public void loginService(String uname, String upass) {

		log.info("Login Employee");

		try {
			if (uname.equalsIgnoreCase("admin")) {
				log.info("User Login ");
				log.debug("Username is {}" , uname);  // Here we have to enable debug level
			}
		} catch (Exception e) {

			log.error("Username Cannot Be Null" + e);

		}

	}

}
