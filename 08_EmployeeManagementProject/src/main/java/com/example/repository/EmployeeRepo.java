package com.example.repository;

import java.util.List;

import com.example.entity.Employee;

public interface EmployeeRepo {

	public void addEmployee(Employee employee);
	
	public List<Employee> checkEmpAvailability();

	List<Employee> getAllEmployees();

}
