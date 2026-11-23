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

@Controller
public class EmployeeController {

	@Autowired
	public EmployeeService employeeService;
	
	@GetMapping("/")
	public String HomePage() {
		
		return "index";
	}
	
	@GetMapping("/reg")
	public String register(@ModelAttribute Employee employee) {
		
		System.out.println("Employee Details : " + employee);
		
		employeeService.addEmployee(employee);
		
		return "login";
	}
	
	@GetMapping("/log")
	public String login( @RequestParam ("username") String username,
			             @RequestParam ("password") String password) {
		
		System.out.println("UserName : " + username);
		System.out.println("Password : " + password);
		
		String response = employeeService.loginService(username,password);
		
		if(response.equals("dashboard")) {
			return "redirect:/dashboard";
		}
		
		return response;
		
	}

	@RequestMapping("/dashboard")
	public String dashboard(Model model) {
		
		List<Employee> allEmployees = employeeService.getAllEmployees();
		
		model.addAttribute("listOfEmp", allEmployees);
		
		return "dashboard";
	}
	
}
