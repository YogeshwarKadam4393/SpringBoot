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
import com.empMgtSytm.enums.EmployeeStatus;
import com.empMgtSytm.repo.DepartmentRepo;
import com.empMgtSytm.repo.EmployeeRepo;

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

//      IllegalArgumentException is a built-in Java exception that means: “the value you passed into
//    	this method is not acceptable.”

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

	/*
	 * @Transactional tells Spring: run everything inside this method as one
	 * database transaction. Either all the database changes succeed together, or
	 * none of them are kept.
	 * 
	 * Why We Not Use List<Employee> Instead we use Iterable<Employee> Because
	 * CrudRepository.saveAll() is declared to return Iterable<Employee>, and Java
	 * does not let you return a wider type where a narrower one is promised.
	 * 
	 * JpaRepository is JPA-specific, so it can promise more and returns List
	 */

	@Override
	@Transactional
	public Iterable<Employee> addAllEmployees(List<Employee> listOfEmployees) {

		if (listOfEmployees == null || listOfEmployees.isEmpty()) {
			log.warn("addAllEmployees() failed: list is null or empty");
			throw new EmployeeFoundNullException("List of employees cannot be null or empty");
		}

		log.info("addAllEmployees() called with {} employees", listOfEmployees.size());

//      Set is used Because it does not allow Duplicate Values 
		Set<String> emailsInRequest = new HashSet<>();
		Set<String> phonesInRequest = new HashSet<>();

		for (Employee employee : listOfEmployees) {

			// duplicates inside the same list
			if (!emailsInRequest.add(employee.getEmail())) {
				log.warn("addAllEmployees() failed: duplicate email in request: {}", employee.getEmail());
				throw new DuplicateEmailException("Duplicate email in request: " + employee.getEmail());
			}
			if (!phonesInRequest.add(employee.getPhoneNumber())) {
				log.warn("addAllEmployees() failed: duplicate phone number in request: {}", employee.getPhoneNumber());
				throw new DuplicatePhoneNumberException(
						"Duplicate phone number in request: " + employee.getPhoneNumber());
			}

			// duplicates already in the database
			if (employeeRepo.existsByEmail(employee.getEmail())) {
				log.warn("addAllEmployees() failed: email already exists in DB: {}", employee.getEmail());
				throw new DuplicateEmailException("Employee with this email already exists: " + employee.getEmail());
			}
			if (employeeRepo.existsByPhoneNumber(employee.getPhoneNumber())) {
				log.warn("addAllEmployees() failed: phone number already exists in DB: {}", employee.getPhoneNumber());
				throw new DuplicatePhoneNumberException(
						"Employee with this phone number already exists: " + employee.getPhoneNumber());
			}
		}

		Iterable<Employee> saved = employeeRepo.saveAll(listOfEmployees);
		log.info("addAllEmployees() saved {} employees successfully", listOfEmployees.size());

		return saved;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	@Override
	public boolean isEmployeeAvailable(long id) {

//      This is Used to Check Whether the Given Id Of Employee Is Available Or Not
//      existById(id) Return Type is Boolean :
//		if Employee Exist    ---> return true
//		if Employee NotExist ---> return false	

		log.debug("isEmployeeAvailable() called for id: {}", id);

		Boolean exist = employeeRepo.existsById(id);

		log.debug("Employee with id {} exists: {}", id, exist);

		return exist;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	@Override
	public String findTotalCountOfEmployees() {

		long count = employeeRepo.count();

		log.info("Total employees count: {}", count);

		return "Total Employees Count : " + count;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	@Override
	public Employee getEmployeebyId(long id) {

		log.info("getEmployeebyId() called for id: {}", id);

//      Optional<T> is a class.    	
//      findById(id) does not return an Employee. It returns Optional<Employee>, which is a box
//      that may or may not contain an employee.

//      Step 1: ask the database for the employee, we get the box (Optional) back
		Optional<Employee> emp = employeeRepo.findById(id);

//      isPresent() is a method of Optional that answers one question: “is there an employee inside the box?”
//      ---> Returns true if the box has a value
//      ---> Returns false if the box is empty        

//      Step 2: isPresent() returns true if the box has an employee, false if the box is empty
		if (emp.isPresent()) {

//          Step 3: employee found, so .get() is safe now, it opens the box and gives the employee
			Employee employee = emp.get();
			log.debug("Employee found with id: {}", id);
			return employee;

		}

		// Step 4: employee not found, so throw our custom exception.
		// @ResponseStatus(HttpStatus.NOT_FOUND) on this exception makes Spring send 404
		// instead of 500
		log.warn("Employee not found with id: {}", id);
		throw new EmployeeNotFoundException("Employee not found with id: " + id);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

//  In Parameter We have to pass List<Integer> but my id is of Long Type so taking List<Long>

	@Override
	public Iterable<Employee> getAllEmployeesByIds(List<Long> ids) {

		log.info("getAllEmployeesByIds() called with ids: {}", ids);

		List<Employee> listOfEmp = new ArrayList<Employee>();

		Iterable<Employee> employee = employeeRepo.findAllById(ids);

		for (Employee allEmployees : employee) {

			if (allEmployees == null) {
				log.warn("getAllEmployeesByIds() failed: null employee found for ids: {}", ids);
				throw new EmployeeNotFoundException("Employee With Given Id is Not Available");
			}

			listOfEmp.add(allEmployees);
		}

		log.info("getAllEmployeesByIds() found {} employees", listOfEmp.size());

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

		return employeeRepo.findAll(Sort.by("firstName").ascending());
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	@Override
	public Iterable<Employee> sortEmployeesBySalary() {

		return employeeRepo.findAll(Sort.by("salary").ascending());
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Iterable<Employee> sortEmployeesByNameAndSalary() {

		return employeeRepo.findAll(Sort.by("firstName").ascending().and(Sort.by("salary").ascending()));
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Iterable<Employee> getAllEmployeesByPageAndSize(int page, int size) {

		log.info("getAllEmployees() called");

		PageRequest pageReq = PageRequest.of(page, size);

		Page<Employee> allEmployees = employeeRepo.findAll(pageReq);

		log.info("getAllEmployees() fetched {} employees");

		return allEmployees;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Iterable<Employee> getAllEmployeesByPageAndSizeWithSortingByFirstName(int page, int size) {

		log.info("getAllEmployeesByPageAndSizeWithSortingByFirstName() called");

		PageRequest pageReq = PageRequest.of(page, size, Sort.by("firstName").ascending());

		Page<Employee> allEmployees = employeeRepo.findAll(pageReq);

		log.info("getAllEmployeesByPageAndSizeWithSortingByFirstName() fetched {} employees");

		return allEmployees;

	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Iterable<Employee> getAllEmployeesByPageAndSizeWithSortingBySalary(int page, int size) {

		log.info("getAllEmployeesByPageAndSizeWithSortingBySalary() called");

		PageRequest pageReq = PageRequest.of(page, size, Sort.by("salary").ascending());

		Page<Employee> allEmployees = employeeRepo.findAll(pageReq);

		log.info("getAllEmployeesByPageAndSizeWithSortingBySalary() fetched employees");

		return allEmployees;

	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	@Override
	public Employee patchEmployeeUpdate(long id, Employee employee) {

		log.info("patchEmployeeUpdate() called for id: {}", id);

		// PATCH means partial update: only the fields sent in the request are changed,
		// all other fields keep their old values.

		// Step 1: ask the database for the existing employee, we get the box (Optional)
		// back
		Optional<Employee> emp = employeeRepo.findById(id);

		// Step 2: if the box is empty, there is nothing to update, so throw our custom
		// exception (404)
		if (!emp.isPresent()) {
			log.warn("patchEmployeeUpdate() failed: employee not found with id: {}", id);
			throw new EmployeeNotFoundException("Employee not found with id: " + id);
		}

		// Step 3: employee found, so .get() is safe now, it opens the box and gives the
		// employee
		Employee existingEmployee = emp.get();

		// Step 4: update a field only if the request contains it (not null)
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

		// Step 5: save and return the updated employee.
		// Since existingEmployee already has an id, save() does an UPDATE, not an
		// INSERT
		Employee updated = employeeRepo.save(existingEmployee);
		log.info("Employee patched successfully with id: {}", id);

		return updated;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	@Transactional()
	public Employee putEmployeeUpdate(long id, Employee employee) {

		log.info("putEmployeeUpdate() called for id: {}", id);

		// PUT means full update: ALL fields are replaced with the values sent in the
		// request.
		// Fields missing from the request are NOT kept, they become null.

		// Step 1: ask the database for the existing employee, we get the box (Optional)
		// back
		Optional<Employee> emp = employeeRepo.findById(id);

		// Step 2: box is empty, nothing to update, so throw our custom exception (404)
		if (!emp.isPresent()) {
			log.warn("putEmployeeUpdate() failed: employee not found with id: {}", id);
			throw new EmployeeNotFoundException("Employee not found with id: " + id);
		}

		// Step 3: employee found, so .get() is safe now
		Employee existingEmployee = emp.get();

		// Step 4: email changed? then check that the new email is not used by someone
		// else
		if (!employee.getEmail().equals(existingEmployee.getEmail())
				&& employeeRepo.existsByEmail(employee.getEmail())) {
			log.warn("putEmployeeUpdate() failed for id {}: email already exists: {}", id, employee.getEmail());
			throw new DuplicateEmailException("Employee with this email already exists: " + employee.getEmail());
		}

		// Step 5: phone changed? same duplicate check
		if (!employee.getPhoneNumber().equals(existingEmployee.getPhoneNumber())
				&& employeeRepo.existsByPhoneNumber(employee.getPhoneNumber())) {
			log.warn("putEmployeeUpdate() failed for id {}: phone number already exists: {}", id,
					employee.getPhoneNumber());
			throw new DuplicatePhoneNumberException(
					"Employee with this phone number already exists: " + employee.getPhoneNumber());
		}

		// Step 6: replace ALL fields (no null checks, this is the difference from
		// PATCH)
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

		// Step 7: id already exists, so save() does an UPDATE
		Employee updated = employeeRepo.save(existingEmployee);
		log.info("Employee updated (PUT) successfully with id: {}", id);

		return updated;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String deleteEmployeeById(long id) {

		log.info("deleteEmployeeById() called for id: {}", id);

//      existsById(id) is a method that comes with CrudRepository. It answers one question: “is 
//		there a row in the table with this id?”
//      ---> Returns true if a row with that id exists
//      ---> Returns false if it does not		

		// Step 1: if no employee has this id, throw our custom exception (404)
		if (!employeeRepo.existsById(id)) {
			log.warn("deleteEmployeeById() failed: employee not found with id: {}", id);
			throw new EmployeeNotFoundException("Employee not found with id: " + id);
		}

		// Step 2: employee exists, so delete it
		employeeRepo.deleteById(id);
		log.info("Employee deleted successfully with id: {}", id);

		// Step 3: return the success message
		return "Employee Deleted Successfully For Given ID : " + id;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	@Override
	@Transactional
	public String deleteAllEmployeesByIds(List<Long> ids) {

		log.info("deleteAllEmployeesByIds() called with ids: {}", ids);

		// Step 1: loop through every id and check it in the database
		for (Long id : ids) {

//	      Step 2: id is NOT present, so throw the exception (404), nothing is deleted
			if (!employeeRepo.existsById(id)) {
				log.warn("deleteAllEmployeesByIds() failed: employee not found with id: {}, nothing deleted", id);
				throw new EmployeeNotFoundException("Employee not found with id: " + id);
			}
		}

		// Step 3: the loop finished without throwing, so ALL ids are present, delete
		// them all
		employeeRepo.deleteAllById(ids);
		log.info("Deleted {} employees successfully", ids.size());

		// Step 4: return the success message
		return "Employees Deleted Successfully For Given IDs : " + ids;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	@Override
	public String deleteEmployeeByObject(long id) {

		log.info("deleteEmployeeByObject() called for id: {}", id);

//      Calling getEmployeeById() to fetch Employee With Given Id

		Employee employee = getEmployeebyId(id);

		employeeRepo.delete(employee);
		log.info("Employee object deleted successfully for id: {}", id);

		return "Employee Object Deleted For Id : " + id;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String deleteAllEmployeeObjectsByIds(List<Long> ids) {

		log.info("deleteAllEmployeeObjectsByIds() called with ids: {}", ids);

		Iterable<Employee> allEmployeesByIds = getAllEmployeesByIds(ids);

		employeeRepo.deleteAll(allEmployeesByIds);
		log.info("Deleted {} employee objects successfully", ((List<Employee>) allEmployeesByIds).size());

		return "All Employee Objects Deleted For GIven Id : " + ids;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	@Override
	public String deleteAllEmployees() {

		log.warn("deleteAllEmployees() called: this will clear the whole employee table");

		Iterable<Employee> allEmployees = getAllEmployees();

		employeeRepo.deleteAll(allEmployees);
		log.warn("All employees deleted, {} records removed", ((List<Employee>) allEmployees).size());

		return "Employees DataBase Is Cleared...";
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ---> JPA REPO METHODS <---
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<Employee> getAllEmployeesByFirstName(String firstName) {

		return employeeRepo.findAllEmployeesByFirstName(firstName);
	}

	@Override
	public List<Employee> getAllEmployeesByLastName(String lastName) {

		return employeeRepo.findAllEmployeesByLastName(lastName);
	}

	@Override
	public List<Employee> getAllEmployeesBySalaryGreaterThan(BigDecimal salary) {

		return employeeRepo.findAllEmployeesBySalaryGreaterThan(salary);
	}

	@Override
	public List<Employee> getAllEmployeesBySalaryGreaterThanEqual(BigDecimal salary) {

		return employeeRepo.findAllEmployeesBySalaryGreaterThanEqual(salary);
	}

	@Override
	public List<Employee> getAllEmployeesBySalaryLessThan(BigDecimal salary) {

		return employeeRepo.findAllEmployeesBySalaryLessThan(salary);
	}

	@Override
	public List<Employee> getAllEmployeesBySalaryLessThanEqual(BigDecimal salary) {

		return employeeRepo.findAllEmployeesBySalaryLessThanEqual(salary);
	}

	@Override
	public List<Employee> getAllEmployeesByFirstNameLike(String ch) {

		// StartWith Example

		return employeeRepo.findAllEmployeesByFirstNameLike(ch + "%");
	}

	@Override
	public List<Employee> getAllEmployeesByLastNameLike(String ch) {

		// StartWith Example

		return employeeRepo.findAllEmployeesByLastNameLike("%" + ch);
	}

	@Override
	public String deleteEmployeeByFirstName(String firstName) {

		employeeRepo.deleteEmployeeByFirstName(firstName);

		return "Employees Deleted Successfully...";
	}

	@Override
	public String deleteEmployeeByLastName(String lastName) {

		employeeRepo.deleteEmployeeByLastName(lastName);

		return "Employees Deleted Successfully...";
	}

	@Override
	public List<Employee> getDistinctByFirstNameAndLastName(String firstName, String lastName) {

		return employeeRepo.findDistinctByFirstNameAndLastName(firstName, lastName);
	}

	@Override
	public List<Employee> getEmployeesByFirstNameAndLastName(String firstName, String lastName) {

		return employeeRepo.findEmployeesByFirstNameAndLastName(firstName, lastName);
	}

	@Override
	public List<Employee> getEmployeesByFirstNameOrLastName(String firstName, String lastName) {

		return employeeRepo.findEmployeesByFirstNameOrLastName(firstName, lastName);
	}

	@Override
	public List<Employee> getEmployeeByCity(String city) {
		return employeeRepo.findByCity(city);
	}

	public List<Employee> getEmployeeByDepartment(String dept) {
		return employeeRepo.findByDepartmentIs(dept);
	}

	public Employee getEmployeeByEmail(String email) {
		return employeeRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("Employee not found"));
	}

	@Override
	public List<Employee> getEmployeeByDesignation(String designation) {

		return employeeRepo.findByDesignationEquals(designation);
	}

	@Override
	public List<Employee> getEmployeesByDateOfJoiningBetween(LocalDate startingDate, LocalDate endingDate) {

		return employeeRepo.findEmployeesByDateOfJoiningBetween(startingDate, endingDate);
	}

	@Override
	public List<Employee> getEmployeesByDateOfJoiningAfter(LocalDate startingDate) {

		return employeeRepo.findEmployeesByDateOfJoiningAfter(startingDate);
	}

	@Override
	public List<Employee> getEmployeeByStatus(EmployeeStatus status) {

		return employeeRepo.findByStatus(status);
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
//                         ---> JPQL QUERIES <---
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	@Override
	public List<Employee> getAllEmployeesByNameQuery(String firstName) {

		return employeeRepo.getAllEmployeesByNameQuery(firstName);
	}

	@Override
	public List<Employee> getAllEmployeesBySalaryGreaterThanQuery(BigDecimal salary) {

		return employeeRepo.getAllEmployeesBySalaryGreaterThanQuery(salary);
	}

	// ---------- Basic Queries ----------

	@Override
	public List<Employee> getAllEmployeesByLastNameQuery(String lastName) {

		return employeeRepo.getByAllEmployeesByLastNameQuery(lastName);
	}

	@Override
	public List<Employee> getAllEmployeesByCityQuery(String city) {

		return employeeRepo.getAllEmployeesByCityQuery(city);
	}

	@Override
	public List<Employee> getAllEmployeesByDepartmentQuery(String dept) {

		return employeeRepo.getAllEmployeesByDepartmentQuery(dept);
	}

	@Override
	public List<Employee> getAllEmployeesByStatusQuery(EmployeeStatus status) {

		return employeeRepo.getAllEmployeesByStatusQuery(status);
	}

	@Override
	public Employee getEmployeeByEmailQuery(String email) {

		log.info("getEmployeeByEmailQuery() called for email: {}", email);

		// Optional is empty -> throw our custom exception (404) instead of
		// RuntimeException (500)
		return employeeRepo.getEmployeeByEmailQuery(email).orElseThrow(() -> {
			log.warn("getEmployeeByEmailQuery() failed: employee not found with email: {}", email);
			return new EmployeeNotFoundException("Employee not found with email: " + email);
		});
	}

	// ---------- Comparison ----------

	@Override
	public List<Employee> getAllEmployeesBySalaryLessThanQuery(BigDecimal salary) {

		return employeeRepo.getAllEmployeesBySalaryLessThanQuery(salary);
	}

	@Override
	public List<Employee> getAllEmployeesBySalaryRangeQuery(BigDecimal min, BigDecimal max) {

		return employeeRepo.getAllEmployeesBySalaryRangeQuery(min, max);
	}

	@Override
	public List<Employee> getAllEmployeesByJoiningDateRangeQuery(LocalDate start, LocalDate end) {

		return employeeRepo.getAllEmployeesByJoiningDateRangeQuery(start, end);
	}

	@Override
	public List<Employee> getAllEmployeesJoinedAfterQuery(LocalDate date) {

		return employeeRepo.getAllEmployeesJoinedAfterQuery(date);
	}

	// ---------- AND / OR ----------

	@Override
	public List<Employee> getAllEmployeesByFullNameQuery(String firstName, String lastName) {

		return employeeRepo.getAllEmployeesByFullNameQuery(firstName, lastName);
	}

	@Override
	public List<Employee> getAllEmployeesByFirstOrLastNameQuery(String firstName, String lastName) {

		return employeeRepo.getAllEmployeesByFirstOrLastNameQuery(firstName, lastName);
	}

	@Override
	public List<Employee> getAllEmployeesByDeptAndSalaryGreaterQuery(String dept, BigDecimal salary) {

		return employeeRepo.getAllEmployeesByDeptAndSalaryGreaterQuery(dept, salary);
	}

	@Override
	public List<Employee> getAllEmployeesByCityAndStatusQuery(String city, EmployeeStatus status) {

		return employeeRepo.getAllEmployeesByCityAndStatusQuery(city, status);
	}

	// ---------- LIKE ----------

	@Override
	public List<Employee> getAllEmployeesByFirstNameStartsWithQuery(String prefix) {

		return employeeRepo.getAllEmployeesByFirstNameStartsWithQuery(prefix);
	}

	@Override
	public List<Employee> getAllEmployeesByLastNameEndsWithQuery(String suffix) {

		return employeeRepo.getAllEmployeesByLastNameEndsWithQuery(suffix);
	}

	@Override
	public List<Employee> getAllEmployeesByFirstNameContainsQuery(String text) {

		return employeeRepo.getAllEmployeesByFirstNameContainsQuery(text);
	}

	@Override
	public List<Employee> searchAllEmployeesByFirstNameIgnoreCaseQuery(String text) {

		return employeeRepo.searchAllEmployeesByFirstNameIgnoreCaseQuery(text);
	}

	// ---------- IN ----------

	@Override
	public List<Employee> getAllEmployeesByCitiesQuery(List<String> cities) {

		if (cities == null || cities.isEmpty()) {
			log.warn("getAllEmployeesByCitiesQuery() failed: cities list is null or empty");
			throw new EmployeeFoundNullException("List of cities cannot be null or empty");
		}

		return employeeRepo.getAllEmployeesByCitiesQuery(cities);
	}

	@Override
	public List<Employee> getAllEmployeesByStatusesQuery(List<EmployeeStatus> statuses) {

		if (statuses == null || statuses.isEmpty()) {
			log.warn("getAllEmployeesByStatusesQuery() failed: statuses list is null or empty");
			throw new EmployeeFoundNullException("List of statuses cannot be null or empty");
		}

		return employeeRepo.getAllEmployeesByStatusesQuery(statuses);
	}

	// ---------- Sorting / Top ----------

	@Override
	public List<Employee> getAllEmployeesOrderBySalaryDescQuery() {

		return employeeRepo.getAllEmployeesAllOrderBySalaryDescQuery();
	}

	@Override
	public List<Employee> getAllEmployeesByDeptOrderByNameQuery(String dept) {

		return employeeRepo.getAllEmployeesByDeptOrderByNameQuery(dept);
	}

	@Override
	public List<Employee> getHighestPaidEmployeesQuery() {

		return employeeRepo.getHighestPaidEmployeesQuery();
	}

	// ---------- Projections ----------

	@Override
	public List<String> getAllFirstNamesQuery() {

		return employeeRepo.getAllFirstNamesQuery();
	}

	@Override
	public List<String> getDistinctCitiesQuery() {

		return employeeRepo.getDistinctCitiesQuery();
	}

	@Override
	public List<Object[]> getNamesByDeptQuery(String dept) {

		return employeeRepo.getNamesByDeptQuery(dept);
	}

	// ---------- Aggregates ----------

	@Override
	public long countByStatusQuery(EmployeeStatus status) {

		return employeeRepo.countByStatusQuery(status);
	}

	@Override
	public BigDecimal getAverageSalaryQuery() {

		return employeeRepo.getAverageSalaryQuery();
	}

	@Override
	public BigDecimal getMaxSalaryQuery() {

		return employeeRepo.getMaxSalaryQuery();
	}

	@Override
	public BigDecimal getMinSalaryQuery() {

		return employeeRepo.getMinSalaryQuery();
	}

	@Override
	public BigDecimal getTotalSalaryByDeptQuery(String dept) {

		return employeeRepo.getTotalSalaryByDeptQuery(dept);
	}

	@Override
	public List<Object[]> countEmployeesPerDeptQuery() {

		return employeeRepo.countEmployeesPerDeptQuery();
	}

	@Override
	public List<Object[]> getDeptsWithAvgSalaryAboveQuery(BigDecimal min) {

//      min.doubleValue() :  It converts a BigDecimal into a primitive double.
		return employeeRepo.getDeptsWithAvgSalaryAboveQuery(min.doubleValue());
	}

	// ---------- Update / Delete ----------

	@Override
	@Transactional
	public String updateEmployeeStatusQuery(EmployeeStatus status, Long id) {

		log.info("updateEmployeeStatusQuery() called for id: {} with status: {}", id, status);

		int rows = employeeRepo.updateEmployeeStatusQuery(status, id);

		// 0 rows changed means no employee has this id
		if (rows == 0) {
			log.warn("updateEmployeeStatusQuery() failed: employee not found with id: {}", id);
			throw new EmployeeNotFoundException("Employee not found with id: " + id);
		}

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

		// Step 1: Fetch all employees from database
		List<Employee> employees = employeeRepo.findAll();

		// Step 2: Create DTO list
		List<EmployeeResponseDTO> employeeDTOList = new ArrayList<>();

		// Step 3: Convert Employee entity into DTO
		for (Employee employee : employees) {

			if (employee == null) {
				throw new EmployeeFoundNullException("Employee Found Null");
			}

			EmployeeResponseDTO responseDTO = modelMapper.map(employee, EmployeeResponseDTO.class);

			// Step 4: Set department details
			if (employee.getDepartment() != null) {

				responseDTO.setDepartmentId(employee.getDepartment().getDepartmentId());

				responseDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
			}

			// Step 5: Add DTO to list
			employeeDTOList.add(responseDTO);
		}

		// Step 6: Return DTO list
		return employeeDTOList;
	}

	@Override
	public EmployeeResponseDTO getEmployeeByIdDTO(Long employeeId) {

		// Step 1: Find Employee By ID
		Employee employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));

		// Step 2: Convert Employee Entity into DTO
		EmployeeResponseDTO responseDTO = modelMapper.map(employee, EmployeeResponseDTO.class);

		// Step 3: Set Department Details
		if (employee.getDepartment() != null) {

			responseDTO.setDepartmentId(employee.getDepartment().getDepartmentId());

			responseDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
		}

		// Step 4: Return Response DTO
		return responseDTO;
	}

	@Override
	public EmployeeResponseDTO updateEmployeeByEmployeeId(Long employeeId, EmployeeRequestDTO employeeDTO) {

		// Step 1: Find Existing Employee
		Employee employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));

		// Step 2: Update Employee Details
		employee.setFirstName(employeeDTO.getFirstName());
		employee.setLastName(employeeDTO.getLastName());
		employee.setUserName(employeeDTO.getUserName());
		employee.setPassword(employeeDTO.getPassword());
		employee.setEmail(employeeDTO.getEmail());
		employee.setPhoneNumber(employeeDTO.getPhoneNumber());
		employee.setCity(employeeDTO.getCity());
		employee.setDesignation(employeeDTO.getDesignation());
		employee.setSalary(employeeDTO.getSalary());

		// Step 3: Update Department If Provided
		if (employeeDTO.getDepartmentId() != null) {

			Department department = departmentRepo.findById(employeeDTO.getDepartmentId())
					.orElseThrow(() -> new DepartmentNotFoundException(
							"Department not found with ID: " + employeeDTO.getDepartmentId()));

			employee.setDepartment(department);
		}

		// Step 4: Save Updated Employee
		Employee updatedEmployee = employeeRepo.save(employee);

		// Step 5: Convert Entity into Response DTO
		EmployeeResponseDTO responseDTO = modelMapper.map(updatedEmployee, EmployeeResponseDTO.class);

		// Step 6: Set Department Details
		if (updatedEmployee.getDepartment() != null) {

			responseDTO.setDepartmentId(updatedEmployee.getDepartment().getDepartmentId());

			responseDTO.setDepartmentName(updatedEmployee.getDepartment().getDepartmentName());
		}

		// Step 7: Return Response
		return responseDTO;
	}

	@Override
	public void deleteEmployeeByEmployeeId(Long employeeId) {

		Employee employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));

		employeeRepo.delete(employee);
	}

	@Override
	public List<EmployeeResponseDTO> addMultipleEmployeesToDepartment(MultipleEmployeeRequestDTO requestDTO) {

		// Step 1: Find the existing department
		Department department = departmentRepo.findById(requestDTO.getDepartmentId()).orElseThrow(
				() -> new DepartmentNotFoundException("Department not found with ID: " + requestDTO.getDepartmentId()));

		// Step 2: Create a list to store employee response DTOs
		List<EmployeeResponseDTO> responseList = new ArrayList<>();

		// Step 3: Process each employee
		for (EmployeeRequestDTO employeeDTO : requestDTO.getEmployees()) {

			// Convert DTO into Employee entity
			Employee employee = modelMapper.map(employeeDTO, Employee.class);

			// Assign the existing department to the employee
			employee.setDepartment(department);

			// Save employee in the database
			Employee savedEmployee = employeeRepo.save(employee);

			// Convert saved entity into response DTO
			EmployeeResponseDTO responseDTO = modelMapper.map(savedEmployee, EmployeeResponseDTO.class);

			// Add department details to the response
			responseDTO.setDepartmentId(department.getDepartmentId());

			responseDTO.setDepartmentName(department.getDepartmentName());

			// Add employee response to the list
			responseList.add(responseDTO);
		}

		// Step 4: Return all saved employees
		return responseList;
	}

}