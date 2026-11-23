package com.empMgtSytm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.empMgtSytm.entity.Department;
import com.empMgtSytm.entity.Employee;
import com.empMgtSytm.service.DepartmentService;

@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@PostMapping("/addSingleDepartmentWithSingleEmployee")
	public ResponseEntity<Department> addSingleDepartmentWithSingleEmployee(@RequestBody Department department) {

		Department dept = departmentService.addSingleDepartmentWithSingleEmployee(department);

		return new ResponseEntity<Department>(dept, HttpStatus.CREATED);
	}

	@PostMapping("/addAllDepartmentsWithEmployees")
	public ResponseEntity<List<Department>> addAllDepartmentsWithEmployees(@RequestBody List<Department> departments) {

		List<Department> dept = departmentService.addAllDepartmentsWithEmployees(departments);

		return new ResponseEntity<List<Department>>(dept, HttpStatus.CREATED);
	}

	@PostMapping("/addOneDepartmentWithMultipleEmployees")
	public ResponseEntity<Department> addOneDepartmentWithMultipleEmployees(@RequestBody Department department) {

		Department dept = departmentService.addOneDepartmentWithMultipleEmployees(department);

		return new ResponseEntity<Department>(dept, HttpStatus.CREATED);
	}

	@GetMapping("/getAllDepartmentsOnly")
	public ResponseEntity<List<Department>> getAllDepartmentsOnly() {

		List<Department> listOfDepartment = departmentService.getAllDepartmentsOnly();

		return new ResponseEntity<List<Department>>(listOfDepartment, HttpStatus.OK);
	}

}
