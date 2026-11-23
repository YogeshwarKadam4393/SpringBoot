package com.empMgtSytm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empMgtSytm.customExceptions.DepartmentFoundNullException;
import com.empMgtSytm.customExceptions.DepartmentNotFoundException;
import com.empMgtSytm.entity.Department;
import com.empMgtSytm.entity.Employee;
import com.empMgtSytm.repo.DepartmentRepo;
import com.empMgtSytm.repo.EmployeeRepo;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	public DepartmentRepo departmentRepo;

	@Autowired
	public EmployeeRepo employeeRepo;

	@Override
	public Department addSingleDepartmentWithSingleEmployee(Department department) {

		Department dept = departmentRepo.save(department);

		return dept;
	}

	@Override
	public List<Department> addAllDepartmentsWithEmployees(List<Department> listOfDepartments) {

		for (Department department : listOfDepartments) {

			if (department.getEmployees() != null) {

				for (Employee employee : department.getEmployees()) {
					employee.setDepartment(department);
				}

			}
		}

		return departmentRepo.saveAll(listOfDepartments);
	}

	@Override
	public Department addOneDepartmentWithMultipleEmployees(Department department) {

		List<Employee> listOfEmployees = department.getEmployees();

		if (listOfEmployees != null) {

			for (Employee employee : listOfEmployees) {
				employee.setDepartment(department);
			}

		}

		Department dept = departmentRepo.save(department);

		return dept;
	}

	@Override
	public List<Department> getAllDepartmentsOnly() {

		List<Department> listOfDept = departmentRepo.findAll();

		return listOfDept;
	}

}
