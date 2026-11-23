
package com.empMgtSytm.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empMgtSytm.customExceptions.DepartmentFoundNullException;
import com.empMgtSytm.customExceptions.DepartmentNotFoundException;
import com.empMgtSytm.customExceptions.DuplicateDepartmentEntryFoundException;
import com.empMgtSytm.dto.DepartmentRequestDTO;
import com.empMgtSytm.dto.DepartmentResponseDTO;
import com.empMgtSytm.dto.EmployeeRequestDTO;
import com.empMgtSytm.dto.EmployeeResponseDTO;
import com.empMgtSytm.entity.Department;
import com.empMgtSytm.entity.Employee;
import com.empMgtSytm.enums.EmployeeStatus;
import com.empMgtSytm.repo.DepartmentRepo;
import com.empMgtSytm.repo.EmployeeRepo;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public DepartmentResponseDTO addDepartment(DepartmentRequestDTO departmentDTO) {

		if (departmentDTO == null) {
			throw new DepartmentFoundNullException("Department DTO Cannot Be Null");
		}

		// Step 1: Check whether the department already exists
		Department existingDepartment = departmentRepo
				.findDepartmentByDepartmentName(departmentDTO.getDepartmentName());

		if (existingDepartment != null) {
			throw new DuplicateDepartmentEntryFoundException("Department With This Name Already Exists");
		}

		// Step 2: Convert DTO to Entity
		Department department = modelMapper.map(departmentDTO, Department.class);

		// Step 3: Save Department
		Department savedDepartment = departmentRepo.save(department);

		// Step 4: Convert Entity to DTO
		DepartmentResponseDTO responseDTO = modelMapper.map(savedDepartment, DepartmentResponseDTO.class);

		// Step 5: Return Response
		return responseDTO;
	}

	@Override
	public EmployeeResponseDTO addEmployee(EmployeeRequestDTO employeeDTO) {

		// Step 1: Find Existing Department
		Department department = departmentRepo.findById(employeeDTO.getDepartmentId())
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found"));

		// Step 2: Convert DTO to Entity
		Employee employee = modelMapper.map(employeeDTO, Employee.class);

		// Step 3: Assign Department
		employee.setDepartment(department);

		// Step 4: Set Employee Status
		employee.setStatus(EmployeeStatus.ACTIVE);

		// Step 5: Save Employee
		Employee savedEmployee = employeeRepo.save(employee);

		// Step 6: Convert Entity to DTO
		EmployeeResponseDTO responseDTO = modelMapper.map(savedEmployee, EmployeeResponseDTO.class);

		// Set Department Details
		responseDTO.setDepartmentId(department.getDepartmentId());
		responseDTO.setDepartmentName(department.getDepartmentName());

		// Step 7: Return Response
		return responseDTO;

	}

	@Override
	public List<DepartmentResponseDTO> getAllDepartments() {

		// Step 1: Get All Departments
		List<Department> departments = departmentRepo.findAll();

		// Step 2: Create Response List
		List<DepartmentResponseDTO> responseList = new ArrayList<>();

		// Step 3: Convert Each Department to DTO
		for (Department department : departments) {

			DepartmentResponseDTO responseDTO = modelMapper.map(department, DepartmentResponseDTO.class);

			// Add Department to Response List
			responseList.add(responseDTO);
		}

		// Step 4: Return Response
		return responseList;
	}

	@Override
	public DepartmentResponseDTO getDepartmentById(Long departmentId) {

		// Step 1: Find Department By ID
		Department department = departmentRepo.findById(departmentId)
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + departmentId));

		// Step 2: Convert Entity to DTO
		DepartmentResponseDTO responseDTO = modelMapper.map(department, DepartmentResponseDTO.class);

		// Step 3: Return Response
		return responseDTO;
	}

	@Override
	public DepartmentResponseDTO updateDepartmentById(Long departmentId, DepartmentRequestDTO departmentDTO) {

		// Step 1: Find Existing Department
		Department department = departmentRepo.findById(departmentId)
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + departmentId));

		// Step 2: Update Department Name
		department.setDepartmentName(departmentDTO.getDepartmentName());

		// Step 3: Save Updated Department
		Department updatedDepartment = departmentRepo.save(department);

		// Step 4: Convert Entity to DTO
		DepartmentResponseDTO responseDTO = modelMapper.map(updatedDepartment, DepartmentResponseDTO.class);

		// Step 5: Return Response
		return responseDTO;
	}

	@Override
	public void deleteDepartmentById(Long departmentId) {

		// Step 1: Find Existing Department
		Department department = departmentRepo.findById(departmentId)
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + departmentId));

		// Step 2: Delete Department
		departmentRepo.delete(department);
	}

}
