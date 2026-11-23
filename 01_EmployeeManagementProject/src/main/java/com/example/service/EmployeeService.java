package com.example.service;

import java.util.List;

import com.example.entity.Employee;

public interface EmployeeService {

	public RuntimeException addEmployee(Employee employee);

	public String loginService(String username, String password);
	
	public List<Employee> getAllEmployees();
	

}
