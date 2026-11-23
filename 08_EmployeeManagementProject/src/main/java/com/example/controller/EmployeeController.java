package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Employee;
import com.example.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EmployeeController {

	@Autowired
	public EmployeeService employeeService;

	@GetMapping("/")
	public String HomePage() {

		log.info("Home Page Request Received");

		return "index";
	}

	@GetMapping("/reg")
	public String register(@ModelAttribute Employee employee) {

		log.info("Employee Registration Request Received");

		log.debug("Registration Request Received for Email Address : {}", employee.getEmailid());

		employeeService.addEmployee(employee);

		log.info("Registration request Processed...");

		return "login";
	}

	@GetMapping("/log")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {

		log.info("Login Request Received");

		log.debug("Login Request Received for UserName : {}", username);

		String response = employeeService.loginService(username, password);

		log.debug("Login Service Response : {}", response);

		if (response.equals("dashboard")) {

			log.info("Login Successfull . Redirecting User to Dashboard");

			return "redirect:/dashboard";
		}

		log.warn("Login Failed for Username : {}", username);
		
		return response;

	}

	@RequestMapping("/dashboard")
	public String dashboard(Model model) {

		List<Employee> allEmployees = employeeService.getAllEmployees();

		model.addAttribute("listOfEmp", allEmployees);

		return "dashboard";
	}

}
