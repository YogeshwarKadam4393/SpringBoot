package com.empMgtSytm.service;

import java.util.List;

import com.empMgtSytm.entity.Department;

public interface DepartmentService {
	
	public Department addSingleDepartmentWithSingleEmployee(Department department);
	
	public List<Department> addAllDepartmentsWithEmployees(List<Department> listOfDepartments);

	public Department addOneDepartmentWithMultipleEmployees(Department department);
	
	public List<Department> getAllDepartmentsOnly();

	
}
