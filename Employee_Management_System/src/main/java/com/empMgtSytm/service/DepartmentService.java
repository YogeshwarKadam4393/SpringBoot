package com.empMgtSytm.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.empMgtSytm.dto.DepartmentRequestDTO;
import com.empMgtSytm.dto.DepartmentResponseDTO;
import com.empMgtSytm.dto.EmployeeRequestDTO;
import com.empMgtSytm.dto.EmployeeResponseDTO;

public interface DepartmentService {

	public DepartmentResponseDTO addDepartment(DepartmentRequestDTO departmentDTO);

	// Without Attachment employee creation method
	public EmployeeResponseDTO addEmployee(EmployeeRequestDTO employeeDTO);

	// New employee creation method with attachments
	public EmployeeResponseDTO addEmployee(EmployeeRequestDTO employeeDTO, List<MultipartFile> attachments);

	// Get All Departments
	public List<DepartmentResponseDTO> getAllDepartments();

	// Get Department By ID
	public DepartmentResponseDTO getDepartmentById(Long departmentId);

	// Update Department
	public DepartmentResponseDTO updateDepartmentById(Long departmentId, DepartmentRequestDTO departmentDTO);

	// Delete Department
	public void deleteDepartmentById(Long departmentId);
}
