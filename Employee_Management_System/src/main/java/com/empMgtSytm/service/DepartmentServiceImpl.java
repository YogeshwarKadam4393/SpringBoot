package com.empMgtSytm.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.empMgtSytm.customExceptions.DepartmentFoundNullException;
import com.empMgtSytm.customExceptions.DepartmentNotFoundException;
import com.empMgtSytm.customExceptions.DuplicateDepartmentEntryFoundException;
import com.empMgtSytm.customExceptions.DuplicateEmailException;
import com.empMgtSytm.customExceptions.DuplicatePhoneNumberException;
import com.empMgtSytm.customExceptions.EmployeeFoundNullException;
import com.empMgtSytm.dto.DepartmentRequestDTO;
import com.empMgtSytm.dto.DepartmentResponseDTO;
import com.empMgtSytm.dto.EmployeeRequestDTO;
import com.empMgtSytm.dto.EmployeeResponseDTO;
import com.empMgtSytm.entity.Department;
import com.empMgtSytm.entity.Employee;
import com.empMgtSytm.repo.DepartmentRepo;
import com.empMgtSytm.repo.EmployeeRepo;
import com.empMgtSytm.utils.EmployeeStatus;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmailService emailService;
	

	// ==================================================
	// METHOD 1: ADD DEPARTMENT
	// ==================================================

	@Override
	public DepartmentResponseDTO addDepartment(DepartmentRequestDTO departmentDTO) {

		log.info("Department creation process started");

		// STEP 1: Check Department DTO

		if (departmentDTO == null) {

			log.warn("Department creation failed: Department DTO is null");

			throw new DepartmentFoundNullException("Department DTO Cannot Be Null");
		}

		// STEP 2: Check Whether Department Already Exists

		log.debug("Checking whether department already exists");

		Department existingDepartment = departmentRepo
				.findDepartmentByDepartmentName(departmentDTO.getDepartmentName());

		if (existingDepartment != null) {

			log.warn("Department creation failed: Duplicate department name found");

			throw new DuplicateDepartmentEntryFoundException("Department With This Name Already Exists");
		}

		// STEP 3: Convert DTO Into Entity

		log.debug("Converting DepartmentRequestDTO into Department Entity");

		Department department = modelMapper.map(departmentDTO, Department.class);

		// STEP 4: Save Department

		log.debug("Saving department into database");

		Department savedDepartment = departmentRepo.save(department);

		log.info("Department saved successfully with ID: {}", savedDepartment.getDepartmentId());

		// STEP 5: Convert Entity Into DTO

		log.debug("Converting Department Entity into DepartmentResponseDTO");

		DepartmentResponseDTO responseDTO = modelMapper.map(savedDepartment, DepartmentResponseDTO.class);

		// STEP 6: Return Response

		log.info("Department creation process completed successfully");

		return responseDTO;
	}

	// ==================================================
	// METHOD 2: ADD EMPLOYEE WITHOUT ATTACHMENTS
	// ==================================================

	@Override
	public EmployeeResponseDTO addEmployee(EmployeeRequestDTO employeeDTO) {

		log.info("Employee creation process started");

		// Step 1: Check Email Already Exists

		log.debug("Checking duplicate employee email");

		if (employeeRepo.existsByEmail(employeeDTO.getEmail())) {

			log.warn("Employee creation failed: Duplicate email found");

			throw new DuplicateEmailException("Email Already Registered: " + employeeDTO.getEmail());
		}

		// Step 2: Check Mobile Number Already Exists

		log.debug("Checking duplicate employee phone number");

		if (employeeRepo.existsByPhoneNumber(employeeDTO.getPhoneNumber())) {

			log.warn("Employee creation failed: Duplicate phone number found");

			throw new DuplicatePhoneNumberException(
					"Mobile Number Already Registered: " + employeeDTO.getPhoneNumber());
		}

		// Step 3: Find Existing Department

		log.debug("Fetching department with ID: {}", employeeDTO.getDepartmentId());

		Department department = departmentRepo.findById(employeeDTO.getDepartmentId()).orElseThrow(() -> {
			log.warn("Employee creation failed: Department not found with ID: {}", employeeDTO.getDepartmentId());

			return new DepartmentNotFoundException("Department not found");
		});

		// Step 4: Convert DTO to Entity

		log.debug("Converting EmployeeRequestDTO into Employee Entity");

		Employee employee = modelMapper.map(employeeDTO, Employee.class);

		// Step 5: Assign Department

		employee.setDepartment(department);

		log.debug("Department assigned to employee successfully");

		// Step 6: Set Employee Status

		employee.setStatus(EmployeeStatus.ACTIVE);

		log.debug("Default employee status set to ACTIVE");

		// Step 7: Save Employee

		Employee savedEmployee = employeeRepo.save(employee);

		log.info("Employee saved successfully with ID: {}", savedEmployee.getId());

		// Step 9: Convert Entity to DTO

		log.debug("Converting Employee Entity into EmployeeResponseDTO");

		EmployeeResponseDTO responseDTO = modelMapper.map(savedEmployee, EmployeeResponseDTO.class);

		// Step 10: Set Department Details

		responseDTO.setDepartmentId(department.getDepartmentId());

		responseDTO.setDepartmentName(department.getDepartmentName());

		// Step 11: Return Response

		log.info("Employee creation process completed successfully. ID: {}", savedEmployee.getId());

		return responseDTO;
	}

	// ==================================================
	// METHOD 3: ADD EMPLOYEE WITH ATTACHMENTS
	// ==================================================

	@Override
	public EmployeeResponseDTO addEmployee(EmployeeRequestDTO employeeDTO, List<MultipartFile> attachments) {

		log.info("Employee creation with attachments process started");

		// STEP 1: Check Employee DTO

		if (employeeDTO == null) {

			log.warn("Employee creation failed: Employee DTO is null");

			throw new EmployeeFoundNullException("Employee Request DTO Cannot Be Null");
		}

		// STEP 2: Check Duplicate Email

		log.debug("Checking duplicate employee email");

		if (employeeRepo.existsByEmail(employeeDTO.getEmail())) {

			log.warn("Employee creation failed: Duplicate email found");

			throw new DuplicateEmailException("Email Already Registered: " + employeeDTO.getEmail());
		}

		// STEP 3: Check Duplicate Phone Number

		log.debug("Checking duplicate employee phone number");

		if (employeeRepo.existsByPhoneNumber(employeeDTO.getPhoneNumber())) {

			log.warn("Employee creation failed: Duplicate phone number found");

			throw new DuplicatePhoneNumberException(
					"Mobile Number Already Registered: " + employeeDTO.getPhoneNumber());
		}

		// STEP 4: Find Existing Department

		log.debug("Fetching department with ID: {}", employeeDTO.getDepartmentId());

		Department department = departmentRepo.findById(employeeDTO.getDepartmentId()).orElseThrow(() -> {
			log.warn("Employee creation failed: Department not found with ID: {}", employeeDTO.getDepartmentId());

			return new DepartmentNotFoundException("Department Not Found With ID: " + employeeDTO.getDepartmentId());
		});

		// STEP 5: Convert DTO Into Employee Entity

		log.debug("Converting EmployeeRequestDTO into Employee Entity");

		Employee employee = modelMapper.map(employeeDTO, Employee.class);

		// STEP 6: Assign Department

		employee.setDepartment(department);

		log.debug("Department assigned to employee successfully");

		// STEP 7: Set Default Employee Status

		employee.setStatus(EmployeeStatus.ACTIVE);

		log.debug("Default employee status set to ACTIVE");

		// STEP 8: Save Employee

		Employee savedEmployee = employeeRepo.save(employee);

		log.info("Employee saved successfully with ID: {}", savedEmployee.getId());

		// STEP 9: Send Welcome Email With Attachments

		log.info("Sending welcome email with attachments for employee ID: {}", savedEmployee.getId());

		log.debug("Number of attachments received: {}", attachments != null ? attachments.size() : 0);

		emailService.sendWelcomeEmail(savedEmployee, attachments);

		log.info("Welcome email with attachments processed for employee ID: {}", savedEmployee.getId());

		// STEP 10: Convert Employee Entity Into Response DTO

		log.debug("Converting Employee Entity into EmployeeResponseDTO");

		EmployeeResponseDTO responseDTO = modelMapper.map(savedEmployee, EmployeeResponseDTO.class);

		// STEP 11: Set Department Details

		responseDTO.setDepartmentId(department.getDepartmentId());

		responseDTO.setDepartmentName(department.getDepartmentName());

		// STEP 12: Return Response DTO

		log.info("Employee creation with attachments completed successfully. ID: {}", savedEmployee.getId());

		return responseDTO;
	}

	// ==================================================
	// METHOD 4: GET ALL DEPARTMENTS
	// ==================================================

	@Override
	public List<DepartmentResponseDTO> getAllDepartments() {

		log.info("Fetching all departments");

		// STEP 1: Get All Departments

		List<Department> departments = departmentRepo.findAll();

		log.debug("Total departments fetched from database: {}", departments.size());

		// STEP 2: Create Response List

		List<DepartmentResponseDTO> responseList = new ArrayList<>();

		// STEP 3: Convert Each Department Into DTO

		log.debug("Converting Department Entities into Response DTOs");

		for (Department department : departments) {

			DepartmentResponseDTO responseDTO = modelMapper.map(department, DepartmentResponseDTO.class);

			responseList.add(responseDTO);
		}

		// STEP 4: Return Response

		log.info("All departments fetched and converted successfully");

		return responseList;
	}

	// ==================================================
	// METHOD 5: GET DEPARTMENT BY ID
	// ==================================================

	@Override
	public DepartmentResponseDTO getDepartmentById(Long departmentId) {

		log.info("Fetching department with ID: {}", departmentId);

		// STEP 1: Find Department By ID

		Department department = departmentRepo.findById(departmentId).orElseThrow(() -> {
			log.warn("Department not found with ID: {}", departmentId);

			return new DepartmentNotFoundException("Department Not Found With ID: " + departmentId);
		});

		// STEP 2: Convert Entity Into DTO

		log.debug("Converting Department Entity into Response DTO");

		DepartmentResponseDTO responseDTO = modelMapper.map(department, DepartmentResponseDTO.class);

		// STEP 3: Return Response

		log.info("Department fetched successfully with ID: {}", departmentId);

		return responseDTO;
	}

	// ==================================================
	// METHOD 6: UPDATE DEPARTMENT BY ID
	// ==================================================

	@Override
	public DepartmentResponseDTO updateDepartmentById(Long departmentId, DepartmentRequestDTO departmentDTO) {

		log.info("Department update process started for ID: {}", departmentId);

		// STEP 1: Find Existing Department

		Department department = departmentRepo.findById(departmentId).orElseThrow(() -> {
			log.warn("Department update failed: Department not found with ID: {}", departmentId);

			return new DepartmentNotFoundException("Department Not Found With ID: " + departmentId);
		});

		// STEP 2: Update Department Name

		department.setDepartmentName(departmentDTO.getDepartmentName());

		log.debug("Department name updated for ID: {}", departmentId);

		// STEP 3: Save Updated Department

		Department updatedDepartment = departmentRepo.save(department);

		log.info("Department updated successfully with ID: {}", departmentId);

		// STEP 4: Convert Entity Into DTO

		log.debug("Converting updated Department Entity into Response DTO");

		DepartmentResponseDTO responseDTO = modelMapper.map(updatedDepartment, DepartmentResponseDTO.class);

		// STEP 5: Return Response

		log.info("Department update process completed successfully");

		return responseDTO;
	}

	// ==================================================
	// METHOD 7: DELETE DEPARTMENT BY ID
	// ==================================================

	@Override
	public void deleteDepartmentById(Long departmentId) {

		log.info("Department deletion process started for ID: {}", departmentId);

		// STEP 1: Find Existing Department

		Department department = departmentRepo.findById(departmentId).orElseThrow(() -> {
			log.warn("Department deletion failed: Department not found with ID: {}", departmentId);

			return new DepartmentNotFoundException("Department Not Found With ID: " + departmentId);
		});

		// STEP 2: Delete Department

		departmentRepo.delete(department);

		log.info("Department deleted successfully with ID: {}", departmentId);
	}

}
