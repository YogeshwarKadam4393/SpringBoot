package com.example.service;

import java.util.List;

import com.example.entity.Employee;

public interface EmployeeService {

	public Employee register(Employee employee);
	
	public String loginService(String username, String password);
	
	public List<Employee> getAllEmployee();
}
