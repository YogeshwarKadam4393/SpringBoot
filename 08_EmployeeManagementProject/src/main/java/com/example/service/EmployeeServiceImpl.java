package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Employee;
import com.example.repository.EmployeeRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	public EmployeeRepo employeeRepo;

	@Override
	public RuntimeException addEmployee(Employee employee) {

		log.trace("Entering addEmployee()");

		log.debug("Checking employee email: {}", employee.getEmailid());

		log.info("Employee registration started");

		List<Employee> availability = employeeRepo.checkEmpAvailability();

		log.debug("Existing employee count: {}", availability.size());

		for (Employee emp : availability) {

			log.trace("Comparing email with existing employee: {}", emp.getEmailid());

			if (employee.getEmailid().equals(emp.getEmailid())) {

				log.warn("Registration rejected. Email already exists: {}", employee.getEmailid());

				return new RuntimeException("User With This Email Address Already Exist...");
			}
		}

		employeeRepo.addEmployee(employee);

		log.info("Employee registered successfully");

		log.trace("Exiting addEmployee()");

		return null;
	}

	@Override
	public String loginService(String username, String password) {

		log.trace("Entering into LoginService()");

		log.debug("Validating login credentials for Username : {}", username);

		if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {

			log.info("Admin Login Successfully");

			log.trace("Exiting LoginService()");

			log.info("Redirecting User to Dashboard");

			return "dashboard";

		} else {

			log.warn("Invalid Login Credentials for Username : {}", username);

			log.trace("Exiting loginService()");
			
			log.info("Redirecting User to ErrorMsg Page");

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
