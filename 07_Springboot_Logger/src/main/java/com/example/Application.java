package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.service.EmployeeService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext cfg = SpringApplication.run(Application.class, args);
		
		EmployeeService emp = cfg.getBean(EmployeeService.class);
		
		emp.loginService("admin", "admin");
	
	}

}
