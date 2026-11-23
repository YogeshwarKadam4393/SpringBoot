package com.empMgtSytm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.empMgtSytm.dto.DepartmentRequestDTO;
import com.empMgtSytm.dto.DepartmentResponseDTO;
import com.empMgtSytm.dto.EmployeeRequestDTO;
import com.empMgtSytm.dto.EmployeeResponseDTO;
import com.empMgtSytm.service.DepartmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	// ==================================================
	// METHOD 1: ADD DEPARTMENT
	// ==================================================

	@PostMapping(value = "/addDepartment")
	public ResponseEntity<DepartmentResponseDTO> addDepartment(@Valid @RequestBody DepartmentRequestDTO departmentDTO) {

		log.info("Request received to add a new department");

		DepartmentResponseDTO department = departmentService.addDepartment(departmentDTO);

		log.info("Department added successfully");

		return new ResponseEntity<DepartmentResponseDTO>(department, HttpStatus.CREATED);
	}

	// ==================================================
	// METHOD 2: ADD EMPLOYEE WITHOUT ATTACHMENTS
	// ==================================================

	@PostMapping("/addEmployee")
	public ResponseEntity<EmployeeResponseDTO> addEmployee(@Valid @RequestBody EmployeeRequestDTO employeeDTO) {

		log.info("Request received to add a new employee");

		// Add Employee
		EmployeeResponseDTO responseDTO = departmentService.addEmployee(employeeDTO);

		log.info("Employee added successfully");

		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	// ==================================================
	// METHOD 3: ADD EMPLOYEE WITH MULTIPLE ATTACHMENTS
	// ==================================================

	@PostMapping(value = "/addEmployeeWithAttachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<EmployeeResponseDTO> addEmployeeWithAttachments(

			@Valid @RequestPart("employee") EmployeeRequestDTO employeeDTO,

			@RequestPart(value = "attachments", required = false) List<MultipartFile> attachments) {

		log.info("Request received to add employee with attachments");

		log.info("Number of attachments received: {}", attachments != null ? attachments.size() : 0);

		// STEP 1: Call Service Method With Attachments
		EmployeeResponseDTO responseDTO = departmentService.addEmployee(employeeDTO, attachments);

		log.info("Employee added successfully with attachment processing completed");

		// STEP 2: Return Response
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	// ==================================================
	// METHOD 4: GET ALL DEPARTMENTS
	// ==================================================

	// Get All Departments
	@GetMapping("/getAllDepartments")
	public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {

		log.info("Request received to fetch all departments");

		// Get All Departments
		List<DepartmentResponseDTO> departments = departmentService.getAllDepartments();

		log.info("Successfully fetched {} departments", departments.size());

		// Return Response
		return new ResponseEntity<>(departments, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 5: GET DEPARTMENT BY DEPARTMENT ID
	// ==================================================

	// Get Department By ID
	@GetMapping("/getDepartmentById/{departmentId}")
	public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable Long departmentId) {

		log.info("Request received to fetch department with ID: {}", departmentId);

		// Get Department
		DepartmentResponseDTO department = departmentService.getDepartmentById(departmentId);

		log.info("Department fetched successfully for ID: {}", departmentId);

		// Return Response
		return new ResponseEntity<>(department, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 6: UPDATE DEPARTMENT BY DEPARTMENT ID
	// ==================================================

	// Update Department
	@PutMapping("/updateDepartmentById/{departmentId}")
	public ResponseEntity<DepartmentResponseDTO> updateDepartmentById(@PathVariable Long departmentId,
			@Valid @RequestBody DepartmentRequestDTO departmentDTO) {

		log.info("Request received to update department with ID: {}", departmentId);

		// Update Department
		DepartmentResponseDTO responseDTO = departmentService.updateDepartmentById(departmentId, departmentDTO);

		log.info("Department updated successfully for ID: {}", departmentId);

		// Return Response
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 7: DELETE DEPARTMENT BY DEPARTMENT ID
	// ==================================================

	// Delete Department
	@DeleteMapping("/deleteDepartmentById/{departmentId}")
	public ResponseEntity<String> deleteDepartmentById(@PathVariable Long departmentId) {

		log.info("Request received to delete department with ID: {}", departmentId);

		// Delete Department
		departmentService.deleteDepartmentById(departmentId);

		log.info("Department deleted successfully for ID: {}", departmentId);

		// Return Response
		return new ResponseEntity<>("Department deleted successfully", HttpStatus.OK);
	}
}
