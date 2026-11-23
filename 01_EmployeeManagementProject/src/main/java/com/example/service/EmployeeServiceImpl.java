package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Employee;
import com.example.repository.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	public EmployeeRepo employeeRepo;

	@Override
	public RuntimeException addEmployee(Employee employee) {

		List<Employee> availability = employeeRepo.checkEmpAvailability();

		for (Employee emp : availability) {

			System.out.println("Existing Email : " + emp.getEmailid());
			
			System.out.println("New Email      : " + employee.getEmailid());

			if (employee.getEmailid().equals(emp.getEmailid())) {

				return new RuntimeException("User With This Email Address Already Exist...");
			}
		}

		employeeRepo.addEmployee(employee);

		return null;
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
	public List<Employee> getAllEmployees() {
		
		List<Employee> allEmployees = employeeRepo.getAllEmployees();
		
		for (Employee employee : allEmployees) {
			System.out.println(employee);
		}
		
		return allEmployees;
	}
	

}
