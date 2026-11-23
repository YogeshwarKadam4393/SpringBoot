package com.employeeCrued.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeCrued.modal.Employee;
import com.employeeCrued.repository.EmployeeRepositoryServices;
import com.employeeCrued.services.EmployeeServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeServicesImpl implements EmployeeServices{

	@Autowired
	EmployeeRepositoryServices employeeRepositoryServices;
	@Override
	public Employee registorEmployee(Employee employee) {
		log.info("Request ricive in service layer{}",employee);
		Employee emp= employeeRepositoryServices.registor(employee);
		log.info("responce send to service layer to controller{}",emp.getName());
		return emp;
		
	}
	@Override
	public Employee getEmployeeDetails(int id) {
		log.info("Request ricive in service layer to fetch employee details using id:{}",id);
		Employee emp= employeeRepositoryServices.getEmployeeDetails(id);
		return emp;
	}

}
