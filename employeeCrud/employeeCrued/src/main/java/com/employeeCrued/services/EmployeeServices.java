package com.employeeCrued.services;

import com.employeeCrued.modal.Employee;

public interface EmployeeServices {
	
	public Employee registorEmployee(Employee employee);
	public Employee getEmployeeDetails(int id);

}
