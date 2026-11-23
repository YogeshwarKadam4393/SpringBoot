package com.empMgtSytm.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empMgtSytm.customExceptions.DepartmentNotFoundException;
import com.empMgtSytm.customExceptions.DuplicateEmailException;
import com.empMgtSytm.customExceptions.DuplicatePhoneNumberException;
import com.empMgtSytm.customExceptions.EmployeeFoundNullException;
import com.empMgtSytm.customExceptions.EmployeeNotFoundException;
import com.empMgtSytm.dto.EmployeeRequestDTO;
import com.empMgtSytm.dto.EmployeeResponseDTO;
import com.empMgtSytm.dto.MultipleEmployeeRequestDTO;
import com.empMgtSytm.entity.Department;
import com.empMgtSytm.entity.Employee;
import com.empMgtSytm.repo.DepartmentRepo;
import com.empMgtSytm.repo.EmployeeRepo;
import com.empMgtSytm.utils.EmployeeStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private ModelMapper modelMapper;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Employee addEmployee(Employee employee) {

		log.info("addEmployee() called");

		if (employee == null) {
			log.warn("addEmployee() failed: employee is null");
			throw new EmployeeFoundNullException("Employee cannot be null");
		}

		if (employeeRepo.existsByEmail(employee.getEmail())) {
			log.warn("addEmployee() failed: duplicate email {}", employee.getEmail());
			throw new DuplicateEmailException("Employee with this email already exists");
		}

		Employee saved = employeeRepo.save(employee);

		log.info("Employee added successfully with id: {}", saved.getId());

		return saved;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	@Transactional
	public Iterable<Employee> addAllEmployees(List<Employee> listOfEmployees) {

		log.info("addAllEmployees() called");

		if (listOfEmployees == null || listOfEmployees.isEmpty()) {
			log.warn("addAllEmployees() failed: list is null or empty");
			throw new EmployeeFoundNullException("List of employees cannot be null or empty");
		}

		log.info("Processing {} employees", listOfEmployees.size());

		Set<String> emailsInRequest = new HashSet<>();
		Set<String> phonesInRequest = new HashSet<>();

		for (Employee employee : listOfEmployees) {

			if (!emailsInRequest.add(employee.getEmail())) {
				log.warn("Duplicate email found inside request");
				throw new DuplicateEmailException("Duplicate email in request: " + employee.getEmail());
			}

			if (!phonesInRequest.add(employee.getPhoneNumber())) {
				log.warn("Duplicate phone number found inside request");
				throw new DuplicatePhoneNumberException(
						"Duplicate phone number in request: " + employee.getPhoneNumber());
			}

			if (employeeRepo.existsByEmail(employee.getEmail())) {
				log.warn("Email already exists in database");
				throw new DuplicateEmailException("Employee with this email already exists: " + employee.getEmail());
			}

			if (employeeRepo.existsByPhoneNumber(employee.getPhoneNumber())) {
				log.warn("Phone number already exists in database");
				throw new DuplicatePhoneNumberException(
						"Employee with this phone number already exists: " + employee.getPhoneNumber());
			}
		}

		Iterable<Employee> saved = employeeRepo.saveAll(listOfEmployees);

		log.info("addAllEmployees() completed successfully");

		return saved;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean isEmployeeAvailable(long id) {

		log.debug("isEmployeeAvailable() called for id: {}", id);

		Boolean exist = employeeRepo.existsById(id);

		log.debug("Employee with id {} exists: {}", id, exist);

		return exist;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String findTotalCountOfEmployees() {

		log.info("findTotalCountOfEmployees() called");

		long count = employeeRepo.count();

		log.info("Total employees count: {}", count);

		return "Total Employees Count : " + count;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Employee getEmployeebyId(long id) {

		log.info("getEmployeebyId() called for id: {}", id);

		Optional<Employee> emp = employeeRepo.findById(id);

		if (emp.isPresent()) {

			Employee employee = emp.get();

			log.debug("Employee found with id: {}", id);

			return employee;

		}

		log.warn("Employee not found with id: {}", id);

		throw new EmployeeNotFoundException("Employee not found with id: " + id);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Iterable<Employee> getAllEmployeesByIds(List<Long> ids) {

		log.info("getAllEmployeesByIds() called");

		List<Employee> listOfEmp = new ArrayList<Employee>();

		Iterable<Employee> employee = employeeRepo.findAllById(ids);

		for (Employee allEmployees : employee) {

			if (allEmployees == null) {
				log.warn("Null employee encountered while fetching employees by IDs");
				throw new EmployeeNotFoundException("Employee With Given Id is Not Available");
			}

			listOfEmp.add(allEmployees);
		}

		log.info("getAllEmployeesByIds() completed. Employees found: {}", listOfEmp.size());

		return listOfEmp;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Iterable<Employee> getAllEmployees() {

		log.info("getAllEmployees() called");

		Iterable<Employee> allEmployees = employeeRepo.findAll();

		log.info("getAllEmployees() fetched {} employees", ((List<Employee>) allEmployees).size());

		return allEmployees;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Iterable<Employee> sortEmployeesByfirstName() {

		log.info("sortEmployeesByfirstName() called");

		Iterable<Employee> employees = employeeRepo.findAll(Sort.by("firstName").ascending());

		log.info("Employees sorted by first name successfully");

		return employees;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Iterable<Employee> sortEmployeesBySalary() {

		log.info("sortEmployeesBySalary() called");

		Iterable<Employee> employees = employeeRepo.findAll(Sort.by("salary").ascending());

		log.info("Employees sorted by salary successfully");

		return employees;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Iterable<Employee> sortEmployeesByNameAndSalary() {

		log.info("sortEmployeesByNameAndSalary() called");

		Iterable<Employee> employees = employeeRepo
				.findAll(Sort.by("firstName").ascending().and(Sort.by("salary").ascending()));

		log.info("Employees sorted by name and salary successfully");

		return employees;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Iterable<Employee> getAllEmployeesByPageAndSize(int page, int size) {

		log.info("getAllEmployeesByPageAndSize() called. Page: {}, Size: {}", page, size);

		PageRequest pageReq = PageRequest.of(page, size);

		Page<Employee> allEmployees = employeeRepo.findAll(pageReq);

		log.info("getAllEmployeesByPageAndSize() fetched {} employees", allEmployees.getNumberOfElements());

		return allEmployees;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Iterable<Employee> getAllEmployeesByPageAndSizeWithSortingByFirstName(int page, int size) {

		log.info("getAllEmployeesByPageAndSizeWithSortingByFirstName() called. Page: {}, Size: {}", page, size);

		PageRequest pageReq = PageRequest.of(page, size, Sort.by("firstName").ascending());

		Page<Employee> allEmployees = employeeRepo.findAll(pageReq);

		log.info("First-name sorted pagination fetched {} employees", allEmployees.getNumberOfElements());

		return allEmployees;

	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Iterable<Employee> getAllEmployeesByPageAndSizeWithSortingBySalary(int page, int size) {

		log.info("getAllEmployeesByPageAndSizeWithSortingBySalary() called. Page: {}, Size: {}", page, size);

		PageRequest pageReq = PageRequest.of(page, size, Sort.by("salary").ascending());

		Page<Employee> allEmployees = employeeRepo.findAll(pageReq);

		log.info("Salary sorted pagination fetched {} employees", allEmployees.getNumberOfElements());

		return allEmployees;

	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Employee patchEmployeeUpdate(long id, Employee employee) {

		log.info("patchEmployeeUpdate() called for id: {}", id);

		Optional<Employee> emp = employeeRepo.findById(id);

		if (!emp.isPresent()) {
			log.warn("patchEmployeeUpdate() failed: employee not found with id: {}", id);
			throw new EmployeeNotFoundException("Employee not found with id: " + id);
		}

		Employee existingEmployee = emp.get();

		if (employee.getFirstName() != null) {
			log.debug("Patching firstName for id: {}", id);
			existingEmployee.setFirstName(employee.getFirstName());
		}

		if (employee.getLastName() != null) {
			log.debug("Patching lastName for id: {}", id);
			existingEmployee.setLastName(employee.getLastName());
		}

		if (employee.getCity() != null) {
			log.debug("Patching city for id: {}", id);
			existingEmployee.setCity(employee.getCity());
		}

		if (employee.getPhoneNumber() != null) {
			log.debug("Patching phoneNumber for id: {}", id);
			existingEmployee.setPhoneNumber(employee.getPhoneNumber());
		}

		if (employee.getEmail() != null) {
			log.debug("Patching email for id: {}", id);
			existingEmployee.setEmail(employee.getEmail());
		}

		if (employee.getDepartment() != null) {
			log.debug("Patching department for id: {}", id);
			existingEmployee.setDepartment(employee.getDepartment());
		}

		if (employee.getDesignation() != null) {
			log.debug("Patching designation for id: {}", id);
			existingEmployee.setDesignation(employee.getDesignation());
		}

		if (employee.getSalary() != null) {
			log.debug("Patching salary for id: {}", id);
			existingEmployee.setSalary(employee.getSalary());
			;
		}

		if (employee.getStatus() != null) {
			log.debug("Patching status for id: {}", id);
			existingEmployee.setStatus(employee.getStatus());
		}

		Employee updated = employeeRepo.save(existingEmployee);

		log.info("Employee patched successfully with id: {}", id);

		return updated;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	@Transactional()
	public Employee putEmployeeUpdate(long id, Employee employee) {

		log.info("putEmployeeUpdate() called for id: {}", id);

		Optional<Employee> emp = employeeRepo.findById(id);

		if (!emp.isPresent()) {
			log.warn("putEmployeeUpdate() failed: employee not found with id: {}", id);
			throw new EmployeeNotFoundException("Employee not found with id: " + id);
		}

		Employee existingEmployee = emp.get();

		if (!employee.getEmail().equals(existingEmployee.getEmail())
				&& employeeRepo.existsByEmail(employee.getEmail())) {
			log.warn("putEmployeeUpdate() failed: duplicate email detected for id: {}", id);
			throw new DuplicateEmailException("Employee with this email already exists: " + employee.getEmail());
		}

		if (!employee.getPhoneNumber().equals(existingEmployee.getPhoneNumber())
				&& employeeRepo.existsByPhoneNumber(employee.getPhoneNumber())) {
			log.warn("putEmployeeUpdate() failed: duplicate phone number detected for id: {}", id);
			throw new DuplicatePhoneNumberException(
					"Employee with this phone number already exists: " + employee.getPhoneNumber());
		}

		log.debug("Replacing all fields for employee id: {}", id);

		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());
		existingEmployee.setPhoneNumber(employee.getPhoneNumber());
		existingEmployee.setCity(employee.getCity());
		existingEmployee.setDepartment(employee.getDepartment());
		existingEmployee.setDesignation(employee.getDesignation());
		existingEmployee.setSalary(employee.getSalary());
		existingEmployee.setDateOfJoining(employee.getDateOfJoining());
		existingEmployee.setStatus(employee.getStatus());

		Employee updated = employeeRepo.save(existingEmployee);

		log.info("Employee updated (PUT) successfully with id: {}", id);

		return updated;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String deleteEmployeeById(long id) {

		log.info("deleteEmployeeById() called for id: {}", id);

		if (!employeeRepo.existsById(id)) {
			log.warn("deleteEmployeeById() failed: employee not found with id: {}", id);
			throw new EmployeeNotFoundException("Employee not found with id: " + id);
		}

		employeeRepo.deleteById(id);

		log.info("Employee deleted successfully with id: {}", id);

		return "Employee Deleted Successfully For Given ID : " + id;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	@Transactional
	public String deleteAllEmployeesByIds(List<Long> ids) {

		log.info("deleteAllEmployeesByIds() called");

		for (Long id : ids) {

			if (!employeeRepo.existsById(id)) {
				log.warn("deleteAllEmployeesByIds() failed: employee not found with id: {}", id);
				throw new EmployeeNotFoundException("Employee not found with id: " + id);
			}
		}

		employeeRepo.deleteAllById(ids);

		log.info("Deleted {} employees successfully", ids.size());

		return "Employees Deleted Successfully For Given IDs : " + ids;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String deleteEmployeeByObject(long id) {

		log.info("deleteEmployeeByObject() called for id: {}", id);

		Employee employee = getEmployeebyId(id);

		employeeRepo.delete(employee);

		log.info("Employee object deleted successfully for id: {}", id);

		return "Employee Object Deleted For Id : " + id;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String deleteAllEmployeeObjectsByIds(List<Long> ids) {

		log.info("deleteAllEmployeeObjectsByIds() called");

		Iterable<Employee> allEmployeesByIds = getAllEmployeesByIds(ids);

		employeeRepo.deleteAll(allEmployeesByIds);

		log.info("deleteAllEmployeeObjectsByIds() completed successfully");

		return "All Employee Objects Deleted For GIven Id : " + ids;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String deleteAllEmployees() {

		log.warn("deleteAllEmployees() called: this will clear the whole employee table");

		Iterable<Employee> allEmployees = getAllEmployees();

		employeeRepo.deleteAll(allEmployees);

		log.warn("All employees deleted successfully");

		return "Employees DataBase Is Cleared...";
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ---> JPA REPO METHODS <---
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<Employee> getAllEmployeesByFirstName(String firstName) {

		log.info("getAllEmployeesByFirstName() called");

		List<Employee> employees = employeeRepo.findAllEmployeesByFirstName(firstName);

		log.info("Employees fetched by first name: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByLastName(String lastName) {

		log.info("getAllEmployeesByLastName() called");

		List<Employee> employees = employeeRepo.findAllEmployeesByLastName(lastName);

		log.info("Employees fetched by last name: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesBySalaryGreaterThan(BigDecimal salary) {

		log.info("getAllEmployeesBySalaryGreaterThan() called");

		List<Employee> employees = employeeRepo.findAllEmployeesBySalaryGreaterThan(salary);

		log.info("Employees fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesBySalaryGreaterThanEqual(BigDecimal salary) {

		log.info("getAllEmployeesBySalaryGreaterThanEqual() called");

		List<Employee> employees = employeeRepo.findAllEmployeesBySalaryGreaterThanEqual(salary);

		log.info("Employees fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesBySalaryLessThan(BigDecimal salary) {

		log.info("getAllEmployeesBySalaryLessThan() called");

		List<Employee> employees = employeeRepo.findAllEmployeesBySalaryLessThan(salary);

		log.info("Employees fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesBySalaryLessThanEqual(BigDecimal salary) {

		log.info("getAllEmployeesBySalaryLessThanEqual() called");

		List<Employee> employees = employeeRepo.findAllEmployeesBySalaryLessThanEqual(salary);

		log.info("Employees fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByFirstNameLike(String ch) {

		log.info("getAllEmployeesByFirstNameLike() called");

		List<Employee> employees = employeeRepo.findAllEmployeesByFirstNameLike(ch + "%");

		log.info("Employees fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByLastNameLike(String ch) {

		log.info("getAllEmployeesByLastNameLike() called");

		List<Employee> employees = employeeRepo.findAllEmployeesByLastNameLike("%" + ch);

		log.info("Employees fetched: {}", employees.size());

		return employees;
	}

	@Override
	public String deleteEmployeeByFirstName(String firstName) {

		log.warn("deleteEmployeeByFirstName() called");

		employeeRepo.deleteEmployeeByFirstName(firstName);

		log.info("Employees deleted by first name successfully");

		return "Employees Deleted Successfully...";
	}

	@Override
	public String deleteEmployeeByLastName(String lastName) {

		log.warn("deleteEmployeeByLastName() called");

		employeeRepo.deleteEmployeeByLastName(lastName);

		log.info("Employees deleted by last name successfully");

		return "Employees Deleted Successfully...";
	}

	@Override
	public List<Employee> getDistinctByFirstNameAndLastName(String firstName, String lastName) {

		log.info("getDistinctByFirstNameAndLastName() called");

		List<Employee> employees = employeeRepo.findDistinctByFirstNameAndLastName(firstName, lastName);

		log.info("Distinct employees fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getEmployeesByFirstNameAndLastName(String firstName, String lastName) {

		log.info("getEmployeesByFirstNameAndLastName() called");

		List<Employee> employees = employeeRepo.findEmployeesByFirstNameAndLastName(firstName, lastName);

		log.info("Employees fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getEmployeesByFirstNameOrLastName(String firstName, String lastName) {

		log.info("getEmployeesByFirstNameOrLastName() called");

		List<Employee> employees = employeeRepo.findEmployeesByFirstNameOrLastName(firstName, lastName);

		log.info("Employees fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getEmployeeByCity(String city) {

		log.info("getEmployeeByCity() called");

		List<Employee> employees = employeeRepo.findByCity(city);

		log.info("Employees fetched by city: {}", employees.size());

		return employees;
	}

	public List<Employee> getEmployeeByDepartment(String dept) {

		log.info("getEmployeeByDepartment() called");

		List<Employee> employees = employeeRepo.findByDepartmentIs(dept);

		log.info("Employees fetched by department: {}", employees.size());

		return employees;
	}

	public Employee getEmployeeByEmail(String email) {

		log.info("getEmployeeByEmail() called");

		return employeeRepo.findByEmail(email).orElseThrow(() -> {

			log.warn("Employee not found for requested email");

			return new RuntimeException("Employee not found");
		});
	}

	@Override
	public List<Employee> getEmployeeByDesignation(String designation) {

		log.info("getEmployeeByDesignation() called");

		List<Employee> employees = employeeRepo.findByDesignationEquals(designation);

		log.info("Employees fetched by designation: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getEmployeesByDateOfJoiningBetween(LocalDate startingDate, LocalDate endingDate) {

		log.info("getEmployeesByDateOfJoiningBetween() called");

		List<Employee> employees = employeeRepo.findEmployeesByDateOfJoiningBetween(startingDate, endingDate);

		log.info("Employees fetched by joining date range: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getEmployeesByDateOfJoiningAfter(LocalDate startingDate) {

		log.info("getEmployeesByDateOfJoiningAfter() called");

		List<Employee> employees = employeeRepo.findEmployeesByDateOfJoiningAfter(startingDate);

		log.info("Employees fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getEmployeeByStatus(EmployeeStatus status) {

		log.info("getEmployeeByStatus() called");

		List<Employee> employees = employeeRepo.findByStatus(status);

		log.info("Employees fetched by status: {}", employees.size());

		return employees;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                         ---> JPQL QUERIES <---
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<Employee> getAllEmployeesByNameQuery(String firstName) {

		log.info("getAllEmployeesByNameQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesByNameQuery(firstName);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesBySalaryGreaterThanQuery(BigDecimal salary) {

		log.info("getAllEmployeesBySalaryGreaterThanQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesBySalaryGreaterThanQuery(salary);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	// ---------- Basic Queries ----------

	@Override
	public List<Employee> getAllEmployeesByLastNameQuery(String lastName) {

		log.info("getAllEmployeesByLastNameQuery() called");

		List<Employee> employees = employeeRepo.getByAllEmployeesByLastNameQuery(lastName);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByCityQuery(String city) {

		log.info("getAllEmployeesByCityQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesByCityQuery(city);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByDepartmentQuery(String dept) {

		log.info("getAllEmployeesByDepartmentQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesByDepartmentQuery(dept);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByStatusQuery(EmployeeStatus status) {

		log.info("getAllEmployeesByStatusQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesByStatusQuery(status);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public Employee getEmployeeByEmailQuery(String email) {

		log.info("getEmployeeByEmailQuery() called");

		return employeeRepo.getEmployeeByEmailQuery(email).orElseThrow(() -> {

			log.warn("Employee not found in JPQL email query");

			return new EmployeeNotFoundException("Employee not found with email: " + email);
		});
	}

	// ---------- Comparison ----------

	@Override
	public List<Employee> getAllEmployeesBySalaryLessThanQuery(BigDecimal salary) {

		log.info("getAllEmployeesBySalaryLessThanQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesBySalaryLessThanQuery(salary);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesBySalaryRangeQuery(BigDecimal min, BigDecimal max) {

		log.info("getAllEmployeesBySalaryRangeQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesBySalaryRangeQuery(min, max);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByJoiningDateRangeQuery(LocalDate start, LocalDate end) {

		log.info("getAllEmployeesByJoiningDateRangeQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesByJoiningDateRangeQuery(start, end);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesJoinedAfterQuery(LocalDate date) {

		log.info("getAllEmployeesJoinedAfterQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesJoinedAfterQuery(date);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	// ---------- AND / OR ----------

	@Override
	public List<Employee> getAllEmployeesByFullNameQuery(String firstName, String lastName) {

		log.info("getAllEmployeesByFullNameQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesByFullNameQuery(firstName, lastName);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByFirstOrLastNameQuery(String firstName, String lastName) {

		log.info("getAllEmployeesByFirstOrLastNameQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesByFirstOrLastNameQuery(firstName, lastName);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByDeptAndSalaryGreaterQuery(String dept, BigDecimal salary) {

		log.info("getAllEmployeesByDeptAndSalaryGreaterQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesByDeptAndSalaryGreaterQuery(dept, salary);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByCityAndStatusQuery(String city, EmployeeStatus status) {

		log.info("getAllEmployeesByCityAndStatusQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesByCityAndStatusQuery(city, status);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	// ---------- LIKE ----------

	@Override
	public List<Employee> getAllEmployeesByFirstNameStartsWithQuery(String prefix) {

		log.info("getAllEmployeesByFirstNameStartsWithQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesByFirstNameStartsWithQuery(prefix);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByLastNameEndsWithQuery(String suffix) {

		log.info("getAllEmployeesByLastNameEndsWithQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesByLastNameEndsWithQuery(suffix);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByFirstNameContainsQuery(String text) {

		log.info("getAllEmployeesByFirstNameContainsQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesByFirstNameContainsQuery(text);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> searchAllEmployeesByFirstNameIgnoreCaseQuery(String text) {

		log.info("searchAllEmployeesByFirstNameIgnoreCaseQuery() called");

		List<Employee> employees = employeeRepo.searchAllEmployeesByFirstNameIgnoreCaseQuery(text);

		log.info("JPQL query completed. Records fetched: {}", employees.size());

		return employees;
	}

	// ---------- IN ----------

	@Override
	public List<Employee> getAllEmployeesByCitiesQuery(List<String> cities) {

		log.info("getAllEmployeesByCitiesQuery() called");

		if (cities == null || cities.isEmpty()) {
			log.warn("getAllEmployeesByCitiesQuery() failed: cities list is null or empty");
			throw new EmployeeFoundNullException("List of cities cannot be null or empty");
		}

		List<Employee> employees = employeeRepo.getAllEmployeesByCitiesQuery(cities);

		log.info("Employees fetched by cities: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByStatusesQuery(List<EmployeeStatus> statuses) {

		log.info("getAllEmployeesByStatusesQuery() called");

		if (statuses == null || statuses.isEmpty()) {
			log.warn("getAllEmployeesByStatusesQuery() failed: statuses list is null or empty");
			throw new EmployeeFoundNullException("List of statuses cannot be null or empty");
		}

		List<Employee> employees = employeeRepo.getAllEmployeesByStatusesQuery(statuses);

		log.info("Employees fetched by statuses: {}", employees.size());

		return employees;
	}

	// ---------- Sorting / Top ----------

	@Override
	public List<Employee> getAllEmployeesOrderBySalaryDescQuery() {

		log.info("getAllEmployeesOrderBySalaryDescQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesAllOrderBySalaryDescQuery();

		log.info("Employees fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesByDeptOrderByNameQuery(String dept) {

		log.info("getAllEmployeesByDeptOrderByNameQuery() called");

		List<Employee> employees = employeeRepo.getAllEmployeesByDeptOrderByNameQuery(dept);

		log.info("Employees fetched: {}", employees.size());

		return employees;
	}

	@Override
	public List<Employee> getHighestPaidEmployeesQuery() {

		log.info("getHighestPaidEmployeesQuery() called");

		List<Employee> employees = employeeRepo.getHighestPaidEmployeesQuery();

		log.info("Highest paid employees fetched: {}", employees.size());

		return employees;
	}

	// ---------- Projections ----------

	@Override
	public List<String> getAllFirstNamesQuery() {

		log.info("getAllFirstNamesQuery() called");

		List<String> firstNames = employeeRepo.getAllFirstNamesQuery();

		log.info("First names fetched: {}", firstNames.size());

		return firstNames;
	}

	@Override
	public List<String> getDistinctCitiesQuery() {

		log.info("getDistinctCitiesQuery() called");

		List<String> cities = employeeRepo.getDistinctCitiesQuery();

		log.info("Distinct cities fetched: {}", cities.size());

		return cities;
	}

	@Override
	public List<Object[]> getNamesByDeptQuery(String dept) {

		log.info("getNamesByDeptQuery() called");

		List<Object[]> employees = employeeRepo.getNamesByDeptQuery(dept);

		log.info("Employee names fetched by department: {}", employees.size());

		return employees;
	}

	// ---------- Aggregates ----------

	@Override
	public long countByStatusQuery(EmployeeStatus status) {

		log.info("countByStatusQuery() called");

		long count = employeeRepo.countByStatusQuery(status);

		log.info("Employee count by status: {}", count);

		return count;
	}

	@Override
	public BigDecimal getAverageSalaryQuery() {

		log.info("getAverageSalaryQuery() called");

		BigDecimal averageSalary = employeeRepo.getAverageSalaryQuery();

		log.info("Average salary query completed");

		return averageSalary;
	}

	@Override
	public BigDecimal getMaxSalaryQuery() {

		log.info("getMaxSalaryQuery() called");

		BigDecimal maxSalary = employeeRepo.getMaxSalaryQuery();

		log.info("Maximum salary query completed");

		return maxSalary;
	}

	@Override
	public BigDecimal getMinSalaryQuery() {

		log.info("getMinSalaryQuery() called");

		BigDecimal minSalary = employeeRepo.getMinSalaryQuery();

		log.info("Minimum salary query completed");

		return minSalary;
	}

	@Override
	public BigDecimal getTotalSalaryByDeptQuery(String dept) {

		log.info("getTotalSalaryByDeptQuery() called");

		BigDecimal totalSalary = employeeRepo.getTotalSalaryByDeptQuery(dept);

		log.info("Total salary by department query completed");

		return totalSalary;
	}

	@Override
	public List<Object[]> countEmployeesPerDeptQuery() {

		log.info("countEmployeesPerDeptQuery() called");

		List<Object[]> result = employeeRepo.countEmployeesPerDeptQuery();

		log.info("Department employee counts fetched: {}", result.size());

		return result;
	}

	@Override
	public List<Object[]> getDeptsWithAvgSalaryAboveQuery(BigDecimal min) {

		log.info("getDeptsWithAvgSalaryAboveQuery() called");

		List<Object[]> result = employeeRepo.getDeptsWithAvgSalaryAboveQuery(min.doubleValue());

		log.info("Departments fetched: {}", result.size());

		return result;
	}

	// ---------- Update / Delete ----------

	@Override
	@Transactional
	public String updateEmployeeStatusQuery(EmployeeStatus status, Long id) {

		log.info("updateEmployeeStatusQuery() called for id: {} with status: {}", id, status);

		int rows = employeeRepo.updateEmployeeStatusQuery(status, id);

		if (rows == 0) {
			log.warn("updateEmployeeStatusQuery() failed: employee not found with id: {}", id);
			throw new EmployeeNotFoundException("Employee not found with id: " + id);
		}

		log.info("Employee status updated successfully for id: {}", id);

		return "Employee Status Updated To " + status + " For Id : " + id;
	}

	@Override
	@Transactional
	public String deleteEmployeeByStatusQuery(EmployeeStatus status) {

		log.warn("deleteEmployeeByStatusQuery() called for status: {}", status);

		int rows = employeeRepo.deleteEmployeeByStatusQuery(status);

		log.warn("Deleted {} employees with status: {}", rows, status);

		return rows + " Employees Deleted With Status : " + status;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                     >>>>>  DTO EMPLOYEE METHODS  <<<<
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<EmployeeResponseDTO> getAllEmployeesDTO() {

		log.info("getAllEmployeesDTO() called");

		List<Employee> employees = employeeRepo.findAll();

		List<EmployeeResponseDTO> employeeDTOList = new ArrayList<>();

		for (Employee employee : employees) {

			if (employee == null) {
				log.warn("getAllEmployeesDTO() failed: employee found null");
				throw new EmployeeFoundNullException("Employee Found Null");
			}

			EmployeeResponseDTO responseDTO = modelMapper.map(employee, EmployeeResponseDTO.class);

			if (employee.getDepartment() != null) {

				responseDTO.setDepartmentId(employee.getDepartment().getDepartmentId());

				responseDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
			}

			employeeDTOList.add(responseDTO);
		}

		log.info("getAllEmployeesDTO() completed. DTOs created: {}", employeeDTOList.size());

		return employeeDTOList;
	}

	@Override
	public EmployeeResponseDTO getEmployeeByIdDTO(Long employeeId) {

		log.info("getEmployeeByIdDTO() called for id: {}", employeeId);

		Employee employee = employeeRepo.findById(employeeId).orElseThrow(() -> {
			log.warn("Employee not found for DTO request. Id: {}", employeeId);
			return new EmployeeNotFoundException("Employee not found with ID: " + employeeId);
		});

		EmployeeResponseDTO responseDTO = modelMapper.map(employee, EmployeeResponseDTO.class);

		if (employee.getDepartment() != null) {

			responseDTO.setDepartmentId(employee.getDepartment().getDepartmentId());

			responseDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
		}

		log.info("Employee response DTO created successfully for id: {}", employeeId);

		return responseDTO;
	}

	@Override
	public EmployeeResponseDTO updateEmployeeByEmployeeId(Long employeeId, EmployeeRequestDTO employeeDTO) {

		log.info("updateEmployeeByEmployeeId() called for id: {}", employeeId);

		Employee employee = employeeRepo.findById(employeeId).orElseThrow(() -> {
			log.warn("Employee not found for update. Id: {}", employeeId);
			return new EmployeeNotFoundException("Employee not found with ID: " + employeeId);
		});

		employee.setFirstName(employeeDTO.getFirstName());
		employee.setLastName(employeeDTO.getLastName());
		employee.setUserName(employeeDTO.getUserName());
		employee.setPassword(employeeDTO.getPassword());
		employee.setEmail(employeeDTO.getEmail());
		employee.setPhoneNumber(employeeDTO.getPhoneNumber());
		employee.setCity(employeeDTO.getCity());
		employee.setDesignation(employeeDTO.getDesignation());
		employee.setSalary(employeeDTO.getSalary());

		if (employeeDTO.getDepartmentId() != null) {

			log.debug("Updating department for employee id: {}", employeeId);

			Department department = departmentRepo.findById(employeeDTO.getDepartmentId()).orElseThrow(() -> {
				log.warn("Department not found. Id: {}", employeeDTO.getDepartmentId());
				return new DepartmentNotFoundException(
						"Department not found with ID: " + employeeDTO.getDepartmentId());
			});

			employee.setDepartment(department);
		}

		Employee updatedEmployee = employeeRepo.save(employee);

		EmployeeResponseDTO responseDTO = modelMapper.map(updatedEmployee, EmployeeResponseDTO.class);

		if (updatedEmployee.getDepartment() != null) {

			responseDTO.setDepartmentId(updatedEmployee.getDepartment().getDepartmentId());

			responseDTO.setDepartmentName(updatedEmployee.getDepartment().getDepartmentName());
		}

		log.info("Employee updated successfully using DTO. Id: {}", employeeId);

		return responseDTO;
	}

	@Override
	public void deleteEmployeeByEmployeeId(Long employeeId) {

		log.info("deleteEmployeeByEmployeeId() called for id: {}", employeeId);

		Employee employee = employeeRepo.findById(employeeId).orElseThrow(() -> {
			log.warn("Employee not found for deletion. Id: {}", employeeId);
			return new EmployeeNotFoundException("Employee not found with ID: " + employeeId);
		});

		employeeRepo.delete(employee);

		log.info("Employee deleted successfully using DTO method. Id: {}", employeeId);
	}

	@Override
	public List<EmployeeResponseDTO> addMultipleEmployeesToDepartment(MultipleEmployeeRequestDTO requestDTO) {

		log.info("addMultipleEmployeesToDepartment() called");

		Department department = departmentRepo.findById(requestDTO.getDepartmentId()).orElseThrow(() -> {
			log.warn("Department not found. Id: {}", requestDTO.getDepartmentId());
			return new DepartmentNotFoundException("Department not found with ID: " + requestDTO.getDepartmentId());
		});

		List<EmployeeResponseDTO> responseList = new ArrayList<>();

		for (EmployeeRequestDTO employeeDTO : requestDTO.getEmployees()) {

			log.debug("Processing employee DTO for department id: {}", department.getDepartmentId());

			Employee employee = modelMapper.map(employeeDTO, Employee.class);

			employee.setDepartment(department);

			Employee savedEmployee = employeeRepo.save(employee);

			EmployeeResponseDTO responseDTO = modelMapper.map(savedEmployee, EmployeeResponseDTO.class);

			responseDTO.setDepartmentId(department.getDepartmentId());

			responseDTO.setDepartmentName(department.getDepartmentName());

			responseList.add(responseDTO);
		}

		log.info("Multiple employees added successfully to department. Total employees: {}", responseList.size());

		return responseList;
	}

}
