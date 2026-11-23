package com.employeeCrued.repository;

import com.employeeCrued.modal.Employee;

public interface EmployeeRepositoryServices {

	public Employee registor(Employee employee);
	public Employee getEmployeeDetails(int id);
}
