package com.empMgtSytm.service;

import java.util.List;

import com.empMgtSytm.entity.Employee;

public interface EmployeeService {
	
	public Employee addEmployee(Employee employee);
	
	public Iterable<Employee> addAllEmployees(List<Employee> listOfEmployees);
	
	public boolean isEmployeeAvailable(long id);
	
	public String findTotalCountOfEmployees();

	public Employee getEmployeebyId(long id);
	
	public Iterable<Employee> getAllEmployeesByIds(List<Long> ids);
	
	public Iterable<Employee> getAllEmployees() ;
	
	public Iterable<Employee> sortEmployeesByfirstName() ;
	
	public Iterable<Employee> sortEmployeesBySalary() ;
	
	public Iterable<Employee> sortEmployeesByNameAndSalary() ;
	
	public Iterable<Employee> getAllEmployeesByPageAndSize(int page , int size) ;
	
	public Iterable<Employee> getAllEmployeesByPageAndSizeWithSortingByFirstName(int page , int size) ;

	public Iterable<Employee> getAllEmployeesByPageAndSizeWithSortingBySalary(int page , int size) ;

	public Employee patchEmployeeUpdate(long id, Employee employee);
	
	public Employee putEmployeeUpdate(long id, Employee employee);

	public String deleteEmployeeById(long id);

	public String deleteAllEmployeesByIds(List<Long> ids); 
	
	public String deleteEmployeeByObject(long id);
	
	public String deleteAllEmployeeObjectsByIds(List<Long> ids);
	
	public String deleteAllEmployees();
	
}
