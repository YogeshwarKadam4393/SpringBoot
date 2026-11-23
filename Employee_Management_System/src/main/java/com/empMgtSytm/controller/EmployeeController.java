package com.empMgtSytm.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empMgtSytm.dto.EmployeeRequestDTO;
import com.empMgtSytm.dto.EmployeeResponseDTO;
import com.empMgtSytm.dto.MultipleEmployeeRequestDTO;
import com.empMgtSytm.entity.Employee;
import com.empMgtSytm.service.EmployeeService;
import com.empMgtSytm.utils.EmployeeStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "Employee Controller", description = "This Controller is Responsible For Create, Fetch, Update, Delete Employees")
//@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	// ==================================================
	// METHOD 1: ADD SINGLE EMPLOYEE
	// ==================================================

	@PostMapping("/addEmployee")
	@Operation(summary = "Add Single Employee")
	public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {

		log.info("Add employee process started");

		Employee emp = employeeService.addEmployee(employee);

		log.info("Employee created successfully with ID: {}", emp.getId());

		return new ResponseEntity<Employee>(emp, HttpStatus.CREATED);
	}

	// ==================================================
	// METHOD 2: ADD MULTIPLE EMPLOYEES
	// ==================================================

	@PostMapping("/addAllEmployees")
	@Operation(summary = "Add Multiple Employee")
	public ResponseEntity<Iterable<Employee>> addAllEmployees(@Valid @RequestBody List<Employee> listOfEmployees) {

		log.info("Request received to add multiple employees. Count: {}", listOfEmployees.size());

		Iterable<Employee> listOfEmp = employeeService.addAllEmployees(listOfEmployees);

		log.info("Multiple employees added successfully");

		return new ResponseEntity<Iterable<Employee>>(listOfEmp, HttpStatus.CREATED);
	}

	// ==================================================
	// METHOD 3: CHECK EMPLOYEE AVAILABILITY
	// ==================================================

	@GetMapping("/checkEmployeeAvailabilityById/{id}")
	@Operation(summary = "Check Availability Of Employee By Id")
	public ResponseEntity<Boolean> isEmployeeAvailable(@PathVariable("id") long id) {

		log.debug("Checking employee availability for ID: {}", id);

		boolean existOrNot = employeeService.isEmployeeAvailable(id);

		log.info("Employee availability check completed for ID: {}", id);

		return new ResponseEntity<Boolean>(existOrNot, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 4: FIND TOTAL EMPLOYEE COUNT
	// ==================================================

	@GetMapping("/findTotalCountOfEmployees")
	@Operation(summary = "Find Total Count Of Employees")
	public ResponseEntity<String> findTotalCountOfEmployees() {

		log.info("Request received to find total employee count");

		String message = employeeService.findTotalCountOfEmployees();

		log.info("Total employee count fetched successfully");

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 5: GET EMPLOYEE BY ID
	// ==================================================

	@GetMapping("/getEmployeeById/{id}")
	@Operation(summary = "Get Employee By Id")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {

		log.info("Request received to fetch employee with ID: {}", id);

		Employee employee = employeeService.getEmployeebyId(id);

		log.info("Employee fetched successfully for ID: {}", id);

		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 6: GET EMPLOYEES BY MULTIPLE IDS
	// ==================================================

	@GetMapping("/getAllEmployeesByIds")
	@Operation(summary = "Get Employees By Multiple Ids")
	public ResponseEntity<Iterable<Employee>> getAllEmployeesByIds(@RequestBody List<Long> ids) {

		log.info("Request received to fetch employees by IDs: {}", ids);

		Iterable<Employee> employee = employeeService.getAllEmployeesByIds(ids);

		log.info("Employees fetched successfully for provided IDs");

		return new ResponseEntity<Iterable<Employee>>(employee, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 7: GET ALL EMPLOYEES
	// ==================================================

	@GetMapping("/getAllEmployees")
	@Operation(summary = "Get All Employees")
	public ResponseEntity<Iterable<Employee>> getAllEmployees() {

		log.info("Request received to fetch all employees");

		Iterable<Employee> listOfEmployees = employeeService.getAllEmployees();

		log.info("All employees fetched successfully");

		return new ResponseEntity<Iterable<Employee>>(listOfEmployees, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 8: PAGINATION AND SORTING BY FIRST NAME
	// ==================================================

	@GetMapping("/getAllEmployeesByPageAndSizeWithSortingByFirstName")
	@Operation(summary = "Get All Employees By Sorting By First Name With Page And Size")
	public ResponseEntity<Iterable<Employee>> getAllEmployeesByPageAndSizeWithSortingByFirstName(
			@RequestParam("page") int page, @RequestParam("size") int size) {

		log.info("Fetching employees by first name sorting. Page: {}, Size: {}", page, size);

		Iterable<Employee> listOfEmployees = employeeService.getAllEmployeesByPageAndSizeWithSortingByFirstName(page,
				size);

		log.info("Employees fetched successfully using first name sorting");

		return new ResponseEntity<Iterable<Employee>>(listOfEmployees, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 9: PAGINATION AND SORTING BY SALARY
	// ==================================================

	@GetMapping("/getAllEmployeesByPageAndSizeWithSortingBySalary")
	@Operation(summary = "Get All Employees By Sorting By Salary With Page And Size")
	public ResponseEntity<Iterable<Employee>> getAllEmployeesByPageAndSizeWithSortingBySalary(
			@RequestParam("page") int page, @RequestParam("size") int size) {

		log.info("Fetching employees by salary sorting. Page: {}, Size: {}", page, size);

		Iterable<Employee> listOfEmployees = employeeService.getAllEmployeesByPageAndSizeWithSortingBySalary(page,size);

		log.info("Employees fetched successfully using salary sorting");

		return new ResponseEntity<Iterable<Employee>>(listOfEmployees, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 10: PAGINATION
	// ==================================================

	@GetMapping("/getAllEmployeesByPageAndSize")
	@Operation(summary = "Get All Employees By Page And Size")
	public ResponseEntity<Iterable<Employee>> getAllEmployeesByPageAndSize(@RequestParam("page") int page,
			@RequestParam("size") int size) {

		log.info("Fetching employees using pagination. Page: {}, Size: {}", page, size);

		Iterable<Employee> listOfEmployees = employeeService.getAllEmployeesByPageAndSize(page, size);

		log.info("Employees fetched successfully using pagination");

		return new ResponseEntity<Iterable<Employee>>(listOfEmployees, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 11: SORT EMPLOYEES BY FIRST NAME
	// ==================================================

	@GetMapping("/sortEmployeesByFirstName")
	@Operation(summary = "Sort All Employees By First Name")
	public ResponseEntity<Iterable<Employee>> sortEmployeesByFirstName() {

		log.info("Request received to sort employees by first name");

		Iterable<Employee> sortedListOfEmployeesByFirstName = employeeService.sortEmployeesByfirstName();

		log.info("Employees sorted successfully by first name");

		return new ResponseEntity<Iterable<Employee>>(sortedListOfEmployeesByFirstName, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 12: SORT EMPLOYEES BY NAME AND SALARY
	// ==================================================

	@GetMapping("/sortEmployeesByNameAndSalary")
	@Operation(summary = "Sort All Employees By First Name And Salary")
	public ResponseEntity<Iterable<Employee>> sortEmployeesByNameAndSalary() {

		log.info("Request received to sort employees by name and salary");

		Iterable<Employee> sortEmployeesByNameAndSalary = employeeService.sortEmployeesByNameAndSalary();

		log.info("Employees sorted successfully by name and salary");

		return new ResponseEntity<Iterable<Employee>>(sortEmployeesByNameAndSalary, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 13: SORT EMPLOYEES BY SALARY
	// ==================================================

	@GetMapping("/sortEmployeesBySalary")
	@Operation(summary = "Sort All Employees By Salary")
	public ResponseEntity<Iterable<Employee>> sortEmployeesBySalary() {

		log.info("Request received to sort employees by salary");

		Iterable<Employee> sortedListOfEmployeesBySalary = employeeService.sortEmployeesBySalary();

		log.info("Employees sorted successfully by salary");

		return new ResponseEntity<Iterable<Employee>>(sortedListOfEmployeesBySalary, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 14: PATCH EMPLOYEE
	// ==================================================

	@PatchMapping("/patchEmployeeUpdate/{id}")
	@Operation(summary = "Update Employee Partial Data By Id")
	public ResponseEntity<Employee> patchEmployeeUpdate(@Valid @PathVariable long id, @RequestBody Employee employee) {

		log.info("PATCH request received for employee ID: {}", id);

		Employee updatedEmployee = employeeService.patchEmployeeUpdate(id, employee);

		log.info("Employee partially updated successfully. ID: {}", id);

		return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 15: PUT EMPLOYEE
	// ==================================================

	@PutMapping("/putEmployeeUpdate/{id}")
	@Operation(summary = "Update Whole Employee Data By Id")
	public ResponseEntity<Employee> putEmployeeUpdate(@Valid @PathVariable long id, @RequestBody Employee employee) {

		log.info("PUT request received for employee ID: {}", id);

		Employee updatedEmployee = employeeService.putEmployeeUpdate(id, employee);

		log.info("Employee completely updated successfully. ID: {}", id);

		return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 16: DELETE EMPLOYEE BY ID
	// ==================================================

	@DeleteMapping("/deleteEmployeeById/{id}")
	@Operation(summary = "Delete Employee By Id")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") long id) {

		log.info("Request received to delete employee with ID: {}", id);

		String message = employeeService.deleteEmployeeById(id);

		log.info("Employee deleted successfully. ID: {}", id);

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 17: DELETE MULTIPLE EMPLOYEES BY IDS
	// ==================================================

	@DeleteMapping("/deleteAllEmployeesByIds")
	@Operation(summary = "Delete Multiple Employees By Multiple Ids")
	public ResponseEntity<String> deleteAllEmployeesByIds(@RequestBody List<Long> ids) {

		log.warn("Request received to delete multiple employees. IDs: {}", ids);

		String message = employeeService.deleteAllEmployeesByIds(ids);

		log.info("Multiple employees deleted successfully");

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 18: DELETE EMPLOYEE OBJECT BY ID
	// ==================================================

	@DeleteMapping("/deleteEmployeeObjectById/{id}")
	@Operation(summary = "Delete Employee Entity/Object By Id")
	public ResponseEntity<String> deleteEmployeeByObject(@PathVariable("id") long id) {

		log.info("Request received to delete employee object. ID: {}", id);

		String message = employeeService.deleteEmployeeByObject(id);

		log.info("Employee object deleted successfully. ID: {}", id);

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 19: DELETE MULTIPLE EMPLOYEE OBJECTS
	// ==================================================

	@DeleteMapping("/deleteAllEmployeeObjectsByIds")
	@Operation(summary = "Delete Multiple Employee Entities/Objects By Multiple Ids")
	public ResponseEntity<String> deleteAllEmployeeObjectsByIds(@RequestBody List<Long> ids) {

		log.warn("Request received to delete multiple employee objects. IDs: {}", ids);

		String message = employeeService.deleteAllEmployeeObjectsByIds(ids);

		log.info("Multiple employee objects deleted successfully");

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	// ==================================================
	// METHOD 20: DELETE ALL EMPLOYEES
	// ==================================================

	@DeleteMapping("/deleteAllEmployees")
	@Operation(summary = "Delete All Employees data From dataBase")
	public ResponseEntity<String> deleteAllEmployees() {

		log.warn("Request received to delete ALL employees from database");

		String message = employeeService.deleteAllEmployees();

		log.warn("All employees deleted successfully");

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	// ==================================================
	// SPRING DATA JPA DERIVED QUERY METHODS
	// ==================================================

	@GetMapping("/getAllEmployeesByFirstName")
	public List<Employee> getAllEmployeesByFirstName(@RequestParam("firstName") String firstName) {

		log.info("Fetching employees by first name: {}", firstName);

		List<Employee> employees = employeeService.getAllEmployeesByFirstName(firstName);

		log.info("Employees fetched successfully by first name");

		return employees;
	}

	@GetMapping("/getAllEmployeesByLastName")
	public List<Employee> getAllEmployeesByLastName(@RequestParam("lastName") String lastName) {

		log.info("Fetching employees by last name: {}", lastName);

		List<Employee> employees = employeeService.getAllEmployeesByLastName(lastName);

		log.info("Employees fetched successfully by last name");

		return employees;
	}

	@GetMapping("/getAllEmployeesBySalaryGreaterThan")
	public List<Employee> getAllEmployeesBySalaryGreaterThan(@RequestParam("salary") BigDecimal salary) {

		log.info("Fetching employees with salary greater than: {}", salary);

		List<Employee> employees = employeeService.getAllEmployeesBySalaryGreaterThan(salary);

		log.info("Employees fetched successfully");

		return employees;
	}

	@GetMapping("/getAllEmployeesBySalaryGreaterThanEqual")
	public List<Employee> getAllEmployeesBySalaryGreaterThanEqual(@RequestParam("salary") BigDecimal salary) {

		log.info("Fetching employees with salary greater than or equal to: {}", salary);

		List<Employee> employees = employeeService.getAllEmployeesBySalaryGreaterThanEqual(salary);

		log.info("Employees fetched successfully");

		return employees;
	}

	@GetMapping("/getAllEmployeesBySalaryLessThan")
	public List<Employee> getAllEmployeesBySalaryLessThan(@RequestParam("salary") BigDecimal salary) {

		log.info("Fetching employees with salary less than: {}", salary);

		List<Employee> employees = employeeService.getAllEmployeesBySalaryLessThan(salary);

		log.info("Employees fetched successfully");

		return employees;
	}

	@GetMapping("/getAllEmployeesBySalaryLessThanEqual")
	public List<Employee> getAllEmployeesBySalaryLessThanEqual(@RequestParam("salary") BigDecimal salary) {

		log.info("Fetching employees with salary less than or equal to: {}", salary);

		List<Employee> employees = employeeService.getAllEmployeesBySalaryLessThanEqual(salary);

		log.info("Employees fetched successfully");

		return employees;
	}

	@GetMapping("/getAllEmployeesByFirstNameLike")
	public List<Employee> getAllEmployeesByFirstNameLike(@RequestParam("firstName") String ch) {

		log.info("Searching employees by first name pattern");

		List<Employee> employees = employeeService.getAllEmployeesByFirstNameLike(ch);

		log.info("First name search completed successfully");

		return employees;
	}

	@GetMapping("/getAllEmployeesByLastNameLike")
	public List<Employee> getAllEmployeesByLastNameLike(@RequestParam("lastName") String ch) {

		log.info("Searching employees by last name pattern");

		List<Employee> employees = employeeService.getAllEmployeesByLastNameLike(ch);

		log.info("Last name search completed successfully");

		return employees;
	}

	@DeleteMapping("/deleteEmployeeByFirstName")
	public String deleteEmployeeByFirstName(@RequestParam("firstName") String firstName) {

		log.warn("Request received to delete employees by first name");

		String message = employeeService.deleteEmployeeByFirstName(firstName);

		log.info("Employees deleted successfully by first name");

		return message;
	}

	@DeleteMapping("/deleteEmployeeByLastName")
	public String deleteEmployeeByLastName(@RequestParam("lastName") String lastName) {

		log.warn("Request received to delete employees by last name");

		String message = employeeService.deleteEmployeeByLastName(lastName);

		log.info("Employees deleted successfully by last name");

		return message;
	}

	@GetMapping("/getDistinctByFirstNameAndLastName")
	public List<Employee> getDistinctByFirstNameAndLastName(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {

		log.info("Fetching distinct employees by first name and last name");

		List<Employee> employees = employeeService.getDistinctByFirstNameAndLastName(firstName, lastName);

		log.info("Distinct employee search completed successfully");

		return employees;
	}

	@GetMapping("/getEmployeesByFirstNameAndLastName")
	public List<Employee> getEmployeesByFirstNameAndLastName(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {

		log.info("Fetching employees by first name AND last name");

		List<Employee> employees = employeeService.getEmployeesByFirstNameAndLastName(firstName, lastName);

		log.info("Employee search by AND condition completed");

		return employees;
	}

	@GetMapping("/getEmployeesByFirstNameOrLastName")
	public List<Employee> getEmployeesByFirstNameOrLastName(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {

		log.info("Fetching employees by first name OR last name");

		List<Employee> employees = employeeService.getEmployeesByFirstNameOrLastName(firstName, lastName);

		log.info("Employee search by OR condition completed");

		return employees;
	}

	@GetMapping("getEmployeeByCity")
	public List<Employee> getEmployeeByCity(@RequestParam("city") String city) {

		log.info("Fetching employees by city: {}", city);

		List<Employee> employees = employeeService.getEmployeeByCity(city);

		log.info("Employees fetched successfully by city");

		return employees;
	}

	@GetMapping("/getEmployeeByDepartment")
	public List<Employee> getEmployeeByDepartment(@RequestParam("department") String dept) {

		log.info("Fetching employees by department: {}", dept);

		List<Employee> employees = employeeService.getEmployeeByDepartment(dept);

		log.info("Employees fetched successfully by department");

		return employees;
	}

	@GetMapping("/getEmployeeByEmail")
	public Employee getEmployeeByEmail(@RequestParam("email") String email) {

		log.info("Request received to fetch employee by email");

		Employee employee = employeeService.getEmployeeByEmail(email);

		log.info("Employee fetched successfully by email");

		return employee;
	}

	@GetMapping("/getEmployeeByDesignation")
	public List<Employee> getEmployeeByDesignation(@RequestParam("designation") String designation) {

		log.info("Fetching employees by designation: {}", designation);

		List<Employee> employees = employeeService.getEmployeeByDesignation(designation);

		log.info("Employees fetched successfully by designation");

		return employees;
	}

	@GetMapping("/getEmployeeByStatus")
	public List<Employee> getEmployeeByStatus(@RequestParam("status") EmployeeStatus status) {

		log.info("Fetching employees by status: {}", status);

		List<Employee> employees = employeeService.getEmployeeByStatus(status);

		log.info("Employees fetched successfully by status");

		return employees;
	}

	@GetMapping("/getAllEmployeesByDateOfJoiningBetween")
	public List<Employee> getEmployeesByDateOfJoiningBetween(
			@RequestParam("startingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startingDate,
			@RequestParam("endingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endingDate) {

		log.info("Fetching employees between joining dates: {} and {}", startingDate, endingDate);

		List<Employee> employees = employeeService.getEmployeesByDateOfJoiningBetween(startingDate, endingDate);

		log.info("Employees fetched successfully by joining date range");

		return employees;
	}

	@GetMapping("/getAllEmployeesByDateOfJoiningAfter")
	public List<Employee> getEmployeesByDateOfJoiningAfter(
			@RequestParam("startingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startingDate) {

		log.info("Fetching employees who joined after: {}", startingDate);

		List<Employee> employees = employeeService.getEmployeesByDateOfJoiningAfter(startingDate);

		log.info("Employees fetched successfully by joining date");

		return employees;
	}

	// ==================================================
	// JPQL QUERY METHODS
	// ==================================================

	@GetMapping("/getAllEmployeesByNameQuery")
	public List<Employee> getAllEmployeesByNameQuery(@RequestParam("firstName") String firstName) {

		log.info("JPQL: Fetching employees by first name");

		List<Employee> employees = employeeService.getAllEmployeesByNameQuery(firstName);

		log.info("JPQL first name search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesBySalaryGreaterThanQuery")
	public List<Employee> getAllEmployeesBySalaryGreaterThanQuery(@RequestParam("salary") BigDecimal salary) {

		log.info("JPQL: Fetching employees with salary greater than: {}", salary);

		List<Employee> employees = employeeService.getAllEmployeesBySalaryGreaterThanQuery(salary);

		log.info("JPQL salary search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByLastNameQuery")
	public List<Employee> getAllEmployeesByLastNameQuery(@RequestParam("lastName") String lastName) {

		log.info("JPQL: Fetching employees by last name");

		List<Employee> employees = employeeService.getAllEmployeesByLastNameQuery(lastName);

		log.info("JPQL last name search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByCityQuery")
	public List<Employee> getAllEmployeesByCityQuery(@RequestParam("city") String city) {

		log.info("JPQL: Fetching employees by city: {}", city);

		List<Employee> employees = employeeService.getAllEmployeesByCityQuery(city);

		log.info("JPQL city search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByDepartmentQuery")
	public List<Employee> getAllEmployeesByDepartmentQuery(@RequestParam("department") String dept) {

		log.info("JPQL: Fetching employees by department: {}", dept);

		List<Employee> employees = employeeService.getAllEmployeesByDepartmentQuery(dept);

		log.info("JPQL department search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByStatusQuery")
	public List<Employee> getAllEmployeesByStatusQuery(@RequestParam("status") EmployeeStatus status) {

		log.info("JPQL: Fetching employees by status: {}", status);

		List<Employee> employees = employeeService.getAllEmployeesByStatusQuery(status);

		log.info("JPQL status search completed");

		return employees;
	}

	@GetMapping("/getEmployeeByEmailQuery")
	public Employee getEmployeeByEmailQuery(@RequestParam("email") String email) {

		log.info("JPQL: Request received to fetch employee by email");

		Employee employee = employeeService.getEmployeeByEmailQuery(email);

		log.info("JPQL employee email search completed");

		return employee;
	}

	@GetMapping("/getAllEmployeesBySalaryLessThanQuery")
	public List<Employee> getAllEmployeesBySalaryLessThanQuery(@RequestParam("salary") BigDecimal salary) {

		log.info("JPQL: Fetching employees with salary less than: {}", salary);

		List<Employee> employees = employeeService.getAllEmployeesBySalaryLessThanQuery(salary);

		log.info("JPQL salary search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesBySalaryRangeQuery")
	public List<Employee> getAllEmployeesBySalaryRangeQuery(@RequestParam("min") BigDecimal min,
			@RequestParam("max") BigDecimal max) {

		log.info("JPQL: Fetching employees with salary range between {} and {}", min, max);

		List<Employee> employees = employeeService.getAllEmployeesBySalaryRangeQuery(min, max);

		log.info("JPQL salary range search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByJoiningDateRangeQuery")
	public List<Employee> getAllEmployeesByJoiningDateRangeQuery(
			@RequestParam("startingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startingDate,
			@RequestParam("endingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endingDate) {

		log.info("JPQL: Fetching employees by joining date range");

		List<Employee> employees = employeeService.getAllEmployeesByJoiningDateRangeQuery(startingDate, endingDate);

		log.info("JPQL joining date range search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesJoinedAfterQuery")
	public List<Employee> getAllEmployeesJoinedAfterQuery(
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

		log.info("JPQL: Fetching employees who joined after: {}", date);

		List<Employee> employees = employeeService.getAllEmployeesJoinedAfterQuery(date);

		log.info("JPQL joining date search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByFullNameQuery")
	public List<Employee> getAllEmployeesByFullNameQuery(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {

		log.info("JPQL: Fetching employees by full name");

		List<Employee> employees = employeeService.getAllEmployeesByFullNameQuery(firstName, lastName);

		log.info("JPQL full name search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByFirstOrLastNameQuery")
	public List<Employee> getAllEmployeesByFirstOrLastNameQuery(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {

		log.info("JPQL: Fetching employees by first name OR last name");

		List<Employee> employees = employeeService.getAllEmployeesByFirstOrLastNameQuery(firstName, lastName);

		log.info("JPQL first name OR last name search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByDeptAndSalaryGreaterQuery")
	public List<Employee> getAllEmployeesByDeptAndSalaryGreaterQuery(@RequestParam("department") String dept,
			@RequestParam("salary") BigDecimal salary) {

		log.info("JPQL: Fetching employees by department and minimum salary");

		List<Employee> employees = employeeService.getAllEmployeesByDeptAndSalaryGreaterQuery(dept, salary);

		log.info("JPQL department and salary search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByCityAndStatusQuery")
	public List<Employee> getAllEmployeesByCityAndStatusQuery(@RequestParam("city") String city,
			@RequestParam("status") EmployeeStatus status) {

		log.info("JPQL: Fetching employees by city and status");

		List<Employee> employees = employeeService.getAllEmployeesByCityAndStatusQuery(city, status);

		log.info("JPQL city and status search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByFirstNameStartsWithQuery")
	public List<Employee> getAllEmployeesByFirstNameStartsWithQuery(@RequestParam("prefix") String prefix) {

		log.info("JPQL: Searching employees by first name prefix");

		List<Employee> employees = employeeService.getAllEmployeesByFirstNameStartsWithQuery(prefix);

		log.info("JPQL prefix search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByLastNameEndsWithQuery")
	public List<Employee> getAllEmployeesByLastNameEndsWithQuery(@RequestParam("suffix") String suffix) {

		log.info("JPQL: Searching employees by last name suffix");

		List<Employee> employees = employeeService.getAllEmployeesByLastNameEndsWithQuery(suffix);

		log.info("JPQL suffix search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByFirstNameContainsQuery")
	public List<Employee> getAllEmployeesByFirstNameContainsQuery(@RequestParam("text") String text) {

		log.info("JPQL: Searching employees by first name containing text");

		List<Employee> employees = employeeService.getAllEmployeesByFirstNameContainsQuery(text);

		log.info("JPQL contains search completed");

		return employees;
	}

	@GetMapping("/searchAllEmployeesByFirstNameIgnoreCaseQuery")
	public List<Employee> searchAllEmployeesByFirstNameIgnoreCaseQuery(@RequestParam("text") String text) {

		log.info("JPQL: Searching employees by first name ignoring case");

		List<Employee> employees = employeeService.searchAllEmployeesByFirstNameIgnoreCaseQuery(text);

		log.info("JPQL case-insensitive search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByCitiesQuery")
	public List<Employee> getAllEmployeesByCitiesQuery(@RequestParam("cities") List<String> cities) {

		log.info("JPQL: Fetching employees by multiple cities. Count: {}", cities.size());

		List<Employee> employees = employeeService.getAllEmployeesByCitiesQuery(cities);

		log.info("JPQL multiple city search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByStatusesQuery")
	public List<Employee> getAllEmployeesByStatusesQuery(@RequestParam("statuses") List<EmployeeStatus> statuses) {

		log.info("JPQL: Fetching employees by multiple statuses: {}", statuses);

		List<Employee> employees = employeeService.getAllEmployeesByStatusesQuery(statuses);

		log.info("JPQL multiple status search completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesOrderBySalaryDescQuery")
	public List<Employee> getAllEmployeesOrderBySalaryDescQuery() {

		log.info("JPQL: Fetching employees ordered by salary descending");

		List<Employee> employees = employeeService.getAllEmployeesOrderBySalaryDescQuery();

		log.info("JPQL salary descending sort completed");

		return employees;
	}

	@GetMapping("/getAllEmployeesByDeptOrderByNameQuery")
	public List<Employee> getAllEmployeesByDeptOrderByNameQuery(@RequestParam("department") String dept) {

		log.info("JPQL: Fetching employees by department ordered by name");

		List<Employee> employees = employeeService.getAllEmployeesByDeptOrderByNameQuery(dept);

		log.info("JPQL department name sorting completed");

		return employees;
	}

	@GetMapping("/getHighestPaidEmployeesQuery")
	public List<Employee> getHighestPaidEmployeesQuery() {

		log.info("JPQL: Fetching highest paid employees");

		List<Employee> employees = employeeService.getHighestPaidEmployeesQuery();

		log.info("JPQL highest paid employee search completed");

		return employees;
	}

	@GetMapping("/getAllFirstNamesQuery")
	public List<String> getAllFirstNamesQuery() {

		log.info("JPQL: Fetching all employee first names");

		List<String> names = employeeService.getAllFirstNamesQuery();

		log.info("JPQL first names fetched successfully");

		return names;
	}

	@GetMapping("/getDistinctCitiesQuery")
	public List<String> getDistinctCitiesQuery() {

		log.info("JPQL: Fetching distinct employee cities");

		List<String> cities = employeeService.getDistinctCitiesQuery();

		log.info("JPQL distinct cities fetched successfully");

		return cities;
	}

	@GetMapping("/getNamesByDeptQuery")
	public List<Object[]> getNamesByDeptQuery(@RequestParam("department") String dept) {

		log.info("JPQL: Fetching employee names by department");

		List<Object[]> names = employeeService.getNamesByDeptQuery(dept);

		log.info("JPQL employee names by department fetched successfully");

		return names;
	}

	@GetMapping("/countByStatusQuery")
	public long countByStatusQuery(@RequestParam("status") EmployeeStatus status) {

		log.info("JPQL: Counting employees by status: {}", status);

		long count = employeeService.countByStatusQuery(status);

		log.info("JPQL employee count fetched successfully: {}", count);

		return count;
	}

	@GetMapping("/getAverageSalaryQuery")
	public BigDecimal getAverageSalaryQuery() {

		log.info("JPQL: Calculating average employee salary");

		BigDecimal averageSalary = employeeService.getAverageSalaryQuery();

		log.info("JPQL average salary calculation completed");

		return averageSalary;
	}

	@GetMapping("/getMaxSalaryQuery")
	public BigDecimal getMaxSalaryQuery() {

		log.info("JPQL: Fetching maximum employee salary");

		BigDecimal maxSalary = employeeService.getMaxSalaryQuery();

		log.info("JPQL maximum salary fetched successfully");

		return maxSalary;
	}

	@GetMapping("/getMinSalaryQuery")
	public BigDecimal getMinSalaryQuery() {

		log.info("JPQL: Fetching minimum employee salary");

		BigDecimal minSalary = employeeService.getMinSalaryQuery();

		log.info("JPQL minimum salary fetched successfully");

		return minSalary;
	}

	@GetMapping("/getTotalSalaryByDeptQuery")
	public BigDecimal getTotalSalaryByDeptQuery(@RequestParam("department") String dept) {

		log.info("JPQL: Calculating total salary for department: {}", dept);

		BigDecimal totalSalary = employeeService.getTotalSalaryByDeptQuery(dept);

		log.info("JPQL total salary calculation completed");

		return totalSalary;
	}

	@GetMapping("/countEmployeesPerDeptQuery")
	public List<Object[]> countEmployeesPerDeptQuery() {

		log.info("JPQL: Counting employees department-wise");

		List<Object[]> result = employeeService.countEmployeesPerDeptQuery();

		log.info("JPQL department-wise employee count completed");

		return result;
	}

	@GetMapping("/getDeptsWithAvgSalaryAboveQuery")
	public List<Object[]> getDeptsWithAvgSalaryAboveQuery(@RequestParam("min") BigDecimal min) {

		log.info("JPQL: Fetching departments with average salary above: {}", min);

		List<Object[]> result = employeeService.getDeptsWithAvgSalaryAboveQuery(min);

		log.info("JPQL department average salary search completed");

		return result;
	}

	// ==================================================
	// JPQL UPDATE AND DELETE METHODS
	// ==================================================

	@PatchMapping("/updateEmployeeStatusQuery/{id}")
	public ResponseEntity<String> updateEmployeeStatusQuery(@PathVariable("id") Long id,
			@RequestParam("status") EmployeeStatus status) {

		log.info("Request received to update employee status. ID: {}, Status: {}", id, status);

		String message = employeeService.updateEmployeeStatusQuery(status, id);

		log.info("Employee status updated successfully. ID: {}", id);

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@DeleteMapping("/deleteEmployeeByStatusQuery")
	public ResponseEntity<String> deleteEmployeeByStatusQuery(@RequestParam("status") EmployeeStatus status) {

		log.warn("Request received to delete employees with status: {}", status);

		String message = employeeService.deleteEmployeeByStatusQuery(status);

		log.info("Employees deleted successfully by status");

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	// ==================================================
	// DTO EMPLOYEE METHODS
	// ==================================================

	@GetMapping("/getAllEmployeesDTO")
	public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployeesDTO() {

		log.info("Request received to fetch all employees using DTO");

		List<EmployeeResponseDTO> employees = employeeService.getAllEmployeesDTO();

		log.info("All employee DTOs fetched successfully");

		return ResponseEntity.ok(employees);
	}

	@GetMapping("/getEmployeeByIdDTO/{employeeId}")
	public ResponseEntity<EmployeeResponseDTO> getEmployeeByIdDTO(@PathVariable Long employeeId) {

		log.info("Request received to fetch employee DTO. ID: {}", employeeId);

		EmployeeResponseDTO employee = employeeService.getEmployeeByIdDTO(employeeId);

		log.info("Employee DTO fetched successfully. ID: {}", employeeId);

		return ResponseEntity.ok(employee);
	}

	@PutMapping("/updateEmployeeByEmployeeId/{employeeId}")
	public ResponseEntity<EmployeeResponseDTO> updateEmployeeByEmployeeId(@PathVariable Long employeeId,
			@Valid @RequestBody EmployeeRequestDTO employeeDTO) {

		log.info("Request received to update employee using DTO. ID: {}", employeeId);

		EmployeeResponseDTO response = employeeService.updateEmployeeByEmployeeId(employeeId, employeeDTO);

		log.info("Employee DTO updated successfully. ID: {}", employeeId);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/deleteEmployeeByEmployeeId/{employeeId}")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable Long employeeId) {

		log.info("Request received to delete employee using DTO endpoint. ID: {}", employeeId);

		employeeService.deleteEmployeeById(employeeId);

		log.info("Employee deleted successfully. ID: {}", employeeId);

		return ResponseEntity.ok("Employee deleted successfully with ID: " + employeeId);
	}

	@PostMapping("/addMultipleEmployeesToDepartment")
	public ResponseEntity<List<EmployeeResponseDTO>> addMultipleEmployeesToDepartment(
			@Valid @RequestBody MultipleEmployeeRequestDTO requestDTO) {

		log.info("Request received to add multiple employees to a department");

		List<EmployeeResponseDTO> employees = employeeService.addMultipleEmployeesToDepartment(requestDTO);

		log.info("Multiple employees added to department successfully");

		return ResponseEntity.status(HttpStatus.CREATED).body(employees);
	}
}
