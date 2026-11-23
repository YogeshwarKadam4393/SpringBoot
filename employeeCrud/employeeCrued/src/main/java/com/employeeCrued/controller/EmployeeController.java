package com.employeeCrued.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employeeCrued.modal.Employee;
import com.employeeCrued.services.EmployeeServices;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmployeeController {

	@Autowired
	EmployeeServices employeeServices;

	@PostMapping("/add")
	public ResponseEntity<Employee> registorEmployee(@RequestBody Employee employee) {
		log.info("Employee Details : {}", employee);
		Employee emp = employeeServices.registorEmployee(employee);
		log.info("Request send successfuly");
		return new ResponseEntity<Employee>(emp, HttpStatus.CREATED);
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable int id) {
		Employee emp = employeeServices.getEmployeeDetails(id);
		return new ResponseEntity<Employee>(emp, HttpStatus.OK);

	}

}
