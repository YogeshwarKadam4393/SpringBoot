package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Employee;
import com.example.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	public EmployeeRepository employeeRepository;

	@Override
	public Employee register(Employee employee) {

		Employee emp = employeeRepository.save(employee);
		
		System.out.println("Employee Saved In DataBase Successfully...");
		
		return emp;
	}

	@Override
	public String loginService(String username, String password) {
		
		if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
			return "dashboard";
		} else {
			return "errorMsg";
		}
	}

	@Override
	public List<Employee> getAllEmployee() {
		
		return employeeRepository.findAll();
	}

}
