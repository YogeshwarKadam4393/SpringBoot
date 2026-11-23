package com.empMgtSytm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empMgtSytm.dto.DepartmentRequestDTO;
import com.empMgtSytm.dto.DepartmentResponseDTO;
import com.empMgtSytm.dto.EmployeeRequestDTO;
import com.empMgtSytm.dto.EmployeeResponseDTO;
import com.empMgtSytm.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@PostMapping(value = "/addDepartment")
	public ResponseEntity<DepartmentResponseDTO> addDepartment(@Valid @RequestBody DepartmentRequestDTO departmentDTO) {

		DepartmentResponseDTO department = departmentService.addDepartment(departmentDTO);

		return new ResponseEntity<DepartmentResponseDTO>(department, HttpStatus.CREATED);
	}

	@PostMapping("/addEmployee")
	public ResponseEntity<EmployeeResponseDTO> addEmployee(@Valid @RequestBody EmployeeRequestDTO employeeDTO) {

		// Add Employee
		EmployeeResponseDTO responseDTO = departmentService.addEmployee(employeeDTO);

		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	// Get All Departments
	@GetMapping("/getAllDepartments")
	public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {

		// Get All Departments
		List<DepartmentResponseDTO> departments = departmentService.getAllDepartments();

		// Return Response
		return new ResponseEntity<>(departments, HttpStatus.OK);
	}

	// Get Department By ID
	@GetMapping("/getDepartmentById/{departmentId}")
	public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable Long departmentId) {

		// Get Department
		DepartmentResponseDTO department = departmentService.getDepartmentById(departmentId);

		// Return Response
		return new ResponseEntity<>(department, HttpStatus.OK);
	}

	// Update Department
	@PutMapping("/updateDepartmentById/{departmentId}")
	public ResponseEntity<DepartmentResponseDTO> updateDepartmentById(@PathVariable Long departmentId,
			@Valid @RequestBody DepartmentRequestDTO departmentDTO) {

		// Update Department
		DepartmentResponseDTO responseDTO = departmentService.updateDepartmentById(departmentId, departmentDTO);

		// Return Response
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Delete Department
	@DeleteMapping("/deleteDepartmentById/{departmentId}")
	public ResponseEntity<String> deleteDepartmentById(@PathVariable Long departmentId) {

		// Delete Department
		departmentService.deleteDepartmentById(departmentId);

		// Return Response
		return new ResponseEntity<>("Department deleted successfully", HttpStatus.OK);
	}
}
