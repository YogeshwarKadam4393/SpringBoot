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

import com.empMgtSytm.entity.Employee;
import com.empMgtSytm.enums.EmployeeStatus;
import com.empMgtSytm.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "Employee Controller", description = "This Controller is Responsible For Create , Fetch , Update , Delete  Employees")
//@RequestMapping("/employees")     // Base Path Its a commmon URL Used for Mapping Our Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

//  @Valid tells Spring to check the incoming object against its validation rules before your method
//     runs. If any rule fails, Spring rejects the request automatically and your controller code never executes. 

	@PostMapping("/addEmployee")
	@Operation(summary = "Add Single Employee")
	public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {

		log.info("Add employee process started");

		Employee emp = employeeService.addEmployee(employee);

		log.info("Employee created with id: {}", emp.getId());

		log.info("Add Employee Process Closed for id : {}", emp.getId());

		return new ResponseEntity<Employee>(emp, HttpStatus.CREATED);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

//   In CRUD Repository List is not directly Acccepted So we have to define return type as Iterable in CRUD Repository    
	@PostMapping("/addAllEmployees")
	@Operation(summary = "Add Multiple Employee")
	public ResponseEntity<Iterable<Employee>> addAllEmployees(@Valid @RequestBody List<Employee> listOfEmployees) {

		log.info("POST /addAllEmployees request received");

		Iterable<Employee> listOfEmp = employeeService.addAllEmployees(listOfEmployees);

		log.info("POST /addAllEmployees completed successfully");

		return new ResponseEntity<Iterable<Employee>>(listOfEmp, HttpStatus.CREATED);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	@GetMapping("/checkEmployeeAvailabilityById/{id}")
	@Operation(summary = "Check Availability Of Employee By Id")
	public ResponseEntity<Boolean> isEmployeeAvailable(@PathVariable("id") long id) {

		log.debug("GET /checkEmployeeAvailabilityById/{} request received", id);

		boolean existOrNot = employeeService.isEmployeeAvailable(id);

		log.debug("Availability check for id {} returned: {}", id, existOrNot);

		return new ResponseEntity<Boolean>(existOrNot, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/findTotalCountOfEmployees")
	@Operation(summary = "Find Total Count Of Employees")
	public ResponseEntity<String> findTotalCountOfEmployees() {

		log.info("GET /findTotalCountOfEmployees request received");

		String message = employeeService.findTotalCountOfEmployees();

		log.info("GET /findTotalCountOfEmployees completed: {}", message);

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/getEmployeeById/{id}")
	@Operation(summary = "Get Employee By Id")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {

		log.info("GET /getEmployeeById/{} request received", id);

		log.debug("Employee id : {} ", id);
		Employee employee = employeeService.getEmployeebyId(id);

		log.info("GET /getEmployeeById/{} completed successfully", id);

		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/getAllEmployeesByIds")
	@Operation(summary = "Get Employees By Multiple Ids")
	public ResponseEntity<Iterable<Employee>> getAllEmployeesByIds(@RequestBody List<Long> ids) {

		log.info("GET /getAllEmployeesByIds request received for ids: {}", ids);

		Iterable<Employee> employee = employeeService.getAllEmployeesByIds(ids);

		log.info("GET /getAllEmployeesByIds completed, returning {} employees",
				((@Valid List<Employee>) employee).size());

		return new ResponseEntity<Iterable<Employee>>(employee, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/getAllEmployees")
	@Operation(summary = "Get All Employees")
	public ResponseEntity<Iterable<Employee>> getAllEmployees() {

		log.info("GET /getAllEmployees request received");

		Iterable<Employee> listOfEmployees = employeeService.getAllEmployees();

		log.info("GET /getAllEmployees completed, returning {} employees",
				((@Valid List<Employee>) listOfEmployees).size());

		return new ResponseEntity<Iterable<Employee>>(listOfEmployees, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/getAllEmployeesByPageAndSizeWithSortingByFirstName")
	@Operation(summary = "Get All Employees By Sorting By First Name With Page And Size")
	public ResponseEntity<Iterable<Employee>> getAllEmployeesByPageAndSizeWithSortingByFirstName(
			@RequestParam("page") int page, @RequestParam("size") int size) {

		log.info("GET /getAllEmployeesByPageAndSizeWithSortingByFirstName request received");

		Iterable<Employee> listOfEmployees = employeeService.getAllEmployeesByPageAndSizeWithSortingByFirstName(page,
				size);

		log.info("GET /getAllEmployeesByPageAndSizeWithSortingByFirstName completed, returning employees");

		return new ResponseEntity<Iterable<Employee>>(listOfEmployees, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/getAllEmployeesByPageAndSizeWithSortingBySalary")
	@Operation(summary = "Get All Employees By Sorting By Salary With Page And Size")
	public ResponseEntity<Iterable<Employee>> getAllEmployeesByPageAndSizeWithSortingBySalary(
			@RequestParam("page") int page, @RequestParam("size") int size) {

		log.info("GET /getAllEmployeesByPageAndSizeWithSortingBySalary request received");

		Iterable<Employee> listOfEmployees = employeeService.getAllEmployeesByPageAndSizeWithSortingBySalary(page,
				size);

		log.info("GET /getAllEmployeesByPageAndSizeWithSortingBySalary completed, returning employees");

		return new ResponseEntity<Iterable<Employee>>(listOfEmployees, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/getAllEmployeesByPageAndSize")
	@Operation(summary = "Get All Employees By Page And Size")
	public ResponseEntity<Iterable<Employee>> getAllEmployeesByPageAndSize(@RequestParam("page") int page,
			@RequestParam("size") int size) {

		log.info("GET /getAllEmployeesByPageAndSize request received");

		Iterable<Employee> listOfEmployees = employeeService.getAllEmployeesByPageAndSize(page, size);

		log.info("GET /getAllEmployeesByPageAndSize completed, returning {} employees");

		return new ResponseEntity<Iterable<Employee>>(listOfEmployees, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/sortEmployeesByFirstName")
	@Operation(summary = "Sort All Employees By First Name")
	public ResponseEntity<Iterable<Employee>> sortEmployeesByFirstName() {

		log.info("GET /sortEmployeesByFirstName request received");

		Iterable<Employee> sortedListOfEmployeesByFirstName = employeeService.sortEmployeesByfirstName();

		log.info("GET /sortEmployeesByFirstName completed, returning {} employees");

		return new ResponseEntity<Iterable<Employee>>(sortedListOfEmployeesByFirstName, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/sortEmployeesByNameAndSalary")
	@Operation(summary = "Sort All Employees By First Name And salary")
	public ResponseEntity<Iterable<Employee>> sortEmployeesByNameAndSalary() {

		log.info("GET /sortEmployeesByNameAndSalary request received");

		Iterable<Employee> sortEmployeesByNameAndSalary = employeeService.sortEmployeesByNameAndSalary();

		log.info("GET /sortEmployeesByNameAndSalary completed, returning employees");

		return new ResponseEntity<Iterable<Employee>>(sortEmployeesByNameAndSalary, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/sortEmployeesBySalary")
	@Operation(summary = "Sort All Employees By Salary")
	public ResponseEntity<Iterable<Employee>> sortEmployeesBySalary() {

		log.info("GET /sortEmployeesBySalary request received");

		Iterable<Employee> sortedListOfEmployeesBySalary = employeeService.sortEmployeesBySalary();

		log.info("GET /sortEmployeesByfirstName completed, returning {} employees");

		return new ResponseEntity<Iterable<Employee>>(sortedListOfEmployeesBySalary, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@PatchMapping("/patchEmployeeUpdate/{id}")
	@Operation(summary = "Update Employee Partial Data By Id")
	public ResponseEntity<Employee> patchEmployeeUpdate(@Valid @PathVariable long id, @RequestBody Employee employee) {

		log.info("PATCH /patchEmployeeUpdate/{} request received", id);

		Employee updatedEmployee = employeeService.patchEmployeeUpdate(id, employee);

		log.info("PATCH /patchEmployeeUpdate/{} completed successfully", id);

		return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@PutMapping("/putEmployeeUpdate/{id}")
	@Operation(summary = "Update Whole Employee Data By Id")
	public ResponseEntity<Employee> putEmployeeUpdate(@Valid @PathVariable long id, @RequestBody Employee employee) {

		log.info("PUT /putEmployeeUpdate/{} request received", id);

		Employee updatedEmployee = employeeService.putEmployeeUpdate(id, employee);

		log.info("PUT /putEmployeeUpdate/{} completed successfully", id);

		return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@DeleteMapping("/deleteEmployeeById/{id}")
	@Operation(summary = "Delete Employee By Id")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") long id) {

		log.info("DELETE /deleteEmployeeById/{} request received", id);

		String message = employeeService.deleteEmployeeById(id);

		log.info("DELETE /deleteEmployeeById/{} completed successfully", id);

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	@DeleteMapping("/deleteAllEmployeesByIds")
	@Operation(summary = "Delete Multiple Employees By Multiple Ids")
	public ResponseEntity<String> deleteAllEmployeesByIds(@RequestBody List<Long> ids) {

		log.info("DELETE /deleteAllEmployeesByIds request received for ids: {}", ids);

		String message = employeeService.deleteAllEmployeesByIds(ids);

		log.info("DELETE /deleteAllEmployeesByIds completed successfully");

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@DeleteMapping("/deleteEmployeeObjectById/{id}")
	@Operation(summary = "Delete Employee Entity/Object By Id")
	public ResponseEntity<String> deleteEmployeeByObject(@PathVariable("id") long id) {

		log.info("DELETE /deleteEmployeeObjectById/{} request received", id);

		String message = employeeService.deleteEmployeeByObject(id);

		log.info("DELETE /deleteEmployeeObjectById/{} completed successfully", id);

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@DeleteMapping("/deleteAllEmployeeObjectsByIds")
	@Operation(summary = "Delete Multiple Employee Entities/Objects By Multiple Ids")
	public ResponseEntity<String> deleteAllEmployeeObjectsByIds(@RequestBody List<Long> ids) {

		log.info("DELETE /deleteAllEmployeeObjectsByIds request received for ids: {}", ids);

		String message = employeeService.deleteAllEmployeeObjectsByIds(ids);

		log.info("DELETE /deleteAllEmployeeObjectsByIds completed successfully");

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@DeleteMapping("/deleteAllEmployees")
	@Operation(summary = "Delete All Employees data From dataBase")
	public ResponseEntity<String> deleteAllEmployees() {

		log.warn("DELETE /deleteAllEmployees request received: this will clear the whole employee table");

		String message = employeeService.deleteAllEmployees();

		log.warn("DELETE /deleteAllEmployees completed: all employees deleted");

		return new ResponseEntity<String>(message, HttpStatus.OK);

	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                        --->  JPA REPO METHODS  <---   
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/getAllEmployeesByFirstName")
	public List<Employee> getAllEmployeesByFirstName(@RequestParam("firstName") String firstName) {

		return employeeService.getAllEmployeesByFirstName(firstName);
	}

	@GetMapping("/getAllEmployeesByLastName")
	public List<Employee> getAllEmployeesByLastName(@RequestParam("lastName") String lastName) {

		return employeeService.getAllEmployeesByLastName(lastName);
	}

	@GetMapping("/getAllEmployeesBySalaryGreaterThan")
	public List<Employee> getAllEmployeesBySalaryGreaterThan(@RequestParam("salary") BigDecimal salary) {

		return employeeService.getAllEmployeesBySalaryGreaterThan(salary);
	}

	@GetMapping("/getAllEmployeesBySalaryGreaterThanEqual")
	public List<Employee> getAllEmployeesBySalaryGreaterThanEqual(@RequestParam("salary") BigDecimal salary) {

		return employeeService.getAllEmployeesBySalaryGreaterThanEqual(salary);
	}

	@GetMapping("/getAllEmployeesBySalaryLessThan")
	public List<Employee> getAllEmployeesBySalaryLessThan(@RequestParam("salary") BigDecimal salary) {

		return employeeService.getAllEmployeesBySalaryLessThan(salary);
	}

	@GetMapping("/getAllEmployeesBySalaryLessThanEqual")
	public List<Employee> getAllEmployeesBySalaryLessThanEqual(@RequestParam("salary") BigDecimal salary) {

		return employeeService.getAllEmployeesBySalaryLessThanEqual(salary);
	}

	@GetMapping("/getAllEmployeesByFirstNameLike")
	public List<Employee> getAllEmployeesByFirstNameLike(@RequestParam("firstName") String ch) {

		return employeeService.getAllEmployeesByFirstNameLike(ch);
	}

	@GetMapping("/getAllEmployeesByLastNameLike")
	public List<Employee> getAllEmployeesByLastNameLike(@RequestParam("lastName") String ch) {

		return employeeService.getAllEmployeesByLastNameLike(ch);
	}

	@DeleteMapping("/deleteEmployeeByFirstName")
	public String deleteEmployeeByFirstName(@RequestParam("firstName") String firstName) {

		return employeeService.deleteEmployeeByFirstName(firstName);
	}

	@DeleteMapping("/deleteEmployeeByLastName")
	public String deleteEmployeeByLastName(@RequestParam("lastName") String lastName) {

		return employeeService.deleteEmployeeByLastName(lastName);
	}

	@GetMapping("/getDistinctByFirstNameAndLastName")
	public List<Employee> getDistinctByFirstNameAndLastName(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {

		return employeeService.getDistinctByFirstNameAndLastName(firstName, lastName);
	}

	@GetMapping("/getEmployeesByFirstNameAndLastName")
	public List<Employee> getEmployeesByFirstNameAndLastName(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {

		return employeeService.getEmployeesByFirstNameAndLastName(firstName, lastName);
	}

	@GetMapping("/getEmployeesByFirstNameOrLastName")
	public List<Employee> getEmployeesByFirstNameOrLastName(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {

		return employeeService.getEmployeesByFirstNameOrLastName(firstName, lastName);
	}

	@GetMapping("getEmployeeByCity")
	public List<Employee> getEmployeeByCity(@RequestParam("city") String city) {
		return employeeService.getEmployeeByCity(city);
	}

	@GetMapping("/getEmployeeByDepartment")
	public List<Employee> getEmployeeByDepartment(@RequestParam("department") String dept) {
		return employeeService.getEmployeeByDepartment(dept);
	}

	@GetMapping("/getEmployeeByEmail")
	public Employee getEmployeeByEmail(@RequestParam("email") String email) {
		return employeeService.getEmployeeByEmail(email);
	}

	@GetMapping("/getEmployeeByDesignation")
	public List<Employee> getEmployeeByDesignation(@RequestParam("designation") String designation) {
		return employeeService.getEmployeeByDesignation(designation);
	}

	@GetMapping("/getEmployeeByStatus")
	public List<Employee> getEmployeeByStatus(@RequestParam("status") EmployeeStatus status) {
		return employeeService.getEmployeeByStatus(status);
	}

	@GetMapping("/getAllEmployeesByDateOfJoiningBetween")
	public List<Employee> getEmployeesByDateOfJoiningBetween(
			@RequestParam("startingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startingDate,
			@RequestParam("endingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endingDate) {

		return employeeService.getEmployeesByDateOfJoiningBetween(startingDate, endingDate);
	}

	@GetMapping("/getAllEmployeesByDateOfJoiningAfter")
	public List<Employee> getEmployeesByDateOfJoiningAfter(
			@RequestParam("startingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startingDate) {

		return employeeService.getEmployeesByDateOfJoiningAfter(startingDate);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
//                    >>>> JPQL QUERIES <<<<
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/getAllEmployeesByNameQuery")
	public List<Employee> getAllEmployeesByNameQuery(@RequestParam("firstName") String firstName) {

		return employeeService.getAllEmployeesByNameQuery(firstName);
	}

	@GetMapping("/getAllEmployeesBySalaryGreaterThanQuery")
	public List<Employee> getAllEmployeesBySalaryGreaterThanQuery(@RequestParam("salary") BigDecimal salary) {

		return employeeService.getAllEmployeesBySalaryGreaterThanQuery(salary);
	}

	// ---------- Basic Queries ----------

//  GET /getAllEmployeesByLastNameQuery? lastName=Kadam
	@GetMapping("/getAllEmployeesByLastNameQuery")
	public List<Employee> getAllEmployeesByLastNameQuery(@RequestParam("lastName") String lastName) {

		return employeeService.getAllEmployeesByLastNameQuery(lastName);
	}

	@GetMapping("/getAllEmployeesByCityQuery")
	public List<Employee> getAllEmployeesByCityQuery(@RequestParam("city") String city) {

		return employeeService.getAllEmployeesByCityQuery(city);
	}

	@GetMapping("/getAllEmployeesByDepartmentQuery")
	public List<Employee> getAllEmployeesByDepartmentQuery(@RequestParam("department") String dept) {

		return employeeService.getAllEmployeesByDepartmentQuery(dept);
	}

	// GET /getAllEmployeesByStatusQuery? status=ACTIVE
	@GetMapping("/getAllEmployeesByStatusQuery")
	public List<Employee> getAllEmployeesByStatusQuery(@RequestParam("status") EmployeeStatus status) {

		return employeeService.getAllEmployeesByStatusQuery(status);
	}

	@GetMapping("/getEmployeeByEmailQuery")
	public Employee getEmployeeByEmailQuery(@RequestParam("email") String email) {

		return employeeService.getEmployeeByEmailQuery(email);
	}

	// ---------- Comparison ----------

	@GetMapping("/getAllEmployeesBySalaryLessThanQuery")
	public List<Employee> getAllEmployeesBySalaryLessThanQuery(@RequestParam("salary") BigDecimal salary) {

		return employeeService.getAllEmployeesBySalaryLessThanQuery(salary);
	}

//  GET /getAllEmployeesBySalaryRangeQuery ? min = 30000 & max = 60000
	@GetMapping("/getAllEmployeesBySalaryRangeQuery")
	public List<Employee> getAllEmployeesBySalaryRangeQuery(@RequestParam("min") BigDecimal min,
		                                                 	@RequestParam("max") BigDecimal max) {

		return employeeService.getAllEmployeesBySalaryRangeQuery(min, max);
	}


//  /getAllEmployeesByJoiningDateRangeQuery ? startingDate = 2023-01-01 & endingDate = 2024-01-01
	@GetMapping("/getAllEmployeesByJoiningDateRangeQuery")
	public List<Employee> getAllEmployeesByJoiningDateRangeQuery(
			@RequestParam("startingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startingDate,
			@RequestParam("endingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endingDate) {

		return employeeService.getAllEmployeesByJoiningDateRangeQuery(startingDate, endingDate);
	}

	@GetMapping("/getAllEmployeesJoinedAfterQuery")
	public List<Employee> getAllEmployeesJoinedAfterQuery(
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

		return employeeService.getAllEmployeesJoinedAfterQuery(date);
	}

	// ---------- AND / OR ----------

	@GetMapping("/getAllEmployeesByFullNameQuery")
	public List<Employee> getAllEmployeesByFullNameQuery(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {

		return employeeService.getAllEmployeesByFullNameQuery(firstName, lastName);
	}

	@GetMapping("/getAllEmployeesByFirstOrLastNameQuery")
	public List<Employee> getAllEmployeesByFirstOrLastNameQuery(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {

		return employeeService.getAllEmployeesByFirstOrLastNameQuery(firstName, lastName);
	}

//  GET /getAllEmployeesByDeptAndSalaryGreaterQuery ? department = IT & salary = 50000
	@GetMapping("/getAllEmployeesByDeptAndSalaryGreaterQuery")
	public List<Employee> getAllEmployeesByDeptAndSalaryGreaterQuery(@RequestParam("department") String dept,
			@RequestParam("salary") BigDecimal salary) {

		return employeeService.getAllEmployeesByDeptAndSalaryGreaterQuery(dept, salary);
	}

	@GetMapping("/getAllEmployeesByCityAndStatusQuery")
	public List<Employee> getAllEmployeesByCityAndStatusQuery(@RequestParam("city") String city,
			@RequestParam("status") EmployeeStatus status) {

		return employeeService.getAllEmployeesByCityAndStatusQuery(city, status);
	}

	// ---------- LIKE ----------

//  GET /getAllEmployeesByFirstNameStartsWithQuery ? prefix = Y
	@GetMapping("/getAllEmployeesByFirstNameStartsWithQuery")
	public List<Employee> getAllEmployeesByFirstNameStartsWithQuery(@RequestParam("prefix") String prefix) {

		return employeeService.getAllEmployeesByFirstNameStartsWithQuery(prefix);
	}
	
	
//  GET /getAllEmployeesByLastNameEndsWithQuery ? suffix = Y
	@GetMapping("/getAllEmployeesByLastNameEndsWithQuery")
	public List<Employee> getAllEmployeesByLastNameEndsWithQuery(@RequestParam("suffix") String suffix) {

		return employeeService.getAllEmployeesByLastNameEndsWithQuery(suffix);
	}

//  Search Any Text whatever the text is given according to that it will give Result in firstName Where it contains that text
	@GetMapping("/getAllEmployeesByFirstNameContainsQuery")
	public List<Employee> getAllEmployeesByFirstNameContainsQuery(@RequestParam("text") String text) {

		return employeeService.getAllEmployeesByFirstNameContainsQuery(text);
	}

	@GetMapping("/searchAllEmployeesByFirstNameIgnoreCaseQuery")
	public List<Employee> searchAllEmployeesByFirstNameIgnoreCaseQuery(@RequestParam("text") String text) {

		return employeeService.searchAllEmployeesByFirstNameIgnoreCaseQuery(text);
	}

	// ---------- IN ----------

//  GET /getAllEmployeesByCitiesQuery ? cities = Pune,Mumbai,Delhi
	@GetMapping("/getAllEmployeesByCitiesQuery")
	public List<Employee> getAllEmployeesByCitiesQuery(@RequestParam("cities") List<String> cities) {

		return employeeService.getAllEmployeesByCitiesQuery(cities);
	}

//  GET /getAllEmployeesByStatusesQuery ? statuses = ACTIVE,INACTIVE
	@GetMapping("/getAllEmployeesByStatusesQuery")
	public List<Employee> getAllEmployeesByStatusesQuery(@RequestParam("statuses") List<EmployeeStatus> statuses) {

		return employeeService.getAllEmployeesByStatusesQuery(statuses);
	}

	// ---------- Sorting / Top ----------

	@GetMapping("/getAllEmployeesOrderBySalaryDescQuery")
	public List<Employee> getAllEmployeesOrderBySalaryDescQuery() {

		return employeeService.getAllEmployeesOrderBySalaryDescQuery();
	}

	@GetMapping("/getAllEmployeesByDeptOrderByNameQuery")
	public List<Employee> getAllEmployeesByDeptOrderByNameQuery(@RequestParam("department") String dept) {

		return employeeService.getAllEmployeesByDeptOrderByNameQuery(dept);
	}

	@GetMapping("/getHighestPaidEmployeesQuery")
	public List<Employee> getHighestPaidEmployeesQuery() {

		return employeeService.getHighestPaidEmployeesQuery();
	}

	// ---------- Projections ----------

	@GetMapping("/getAllFirstNamesQuery")
	public List<String> getAllFirstNamesQuery() {

		return employeeService.getAllFirstNamesQuery();
	}

	@GetMapping("/getDistinctCitiesQuery")
	public List<String> getDistinctCitiesQuery() {

		return employeeService.getDistinctCitiesQuery();
	}

	// Returns [["Ravi","Kumar"],["Sita","Rao"]]
	@GetMapping("/getNamesByDeptQuery")
	public List<Object[]> getNamesByDeptQuery(@RequestParam("department") String dept) {

		return employeeService.getNamesByDeptQuery(dept);
	}

	// ---------- Aggregates ----------

	@GetMapping("/countByStatusQuery")
	public long countByStatusQuery(@RequestParam("status") EmployeeStatus status) {

		return employeeService.countByStatusQuery(status);
	}

	@GetMapping("/getAverageSalaryQuery")
	public BigDecimal getAverageSalaryQuery() {

		return employeeService.getAverageSalaryQuery();
	}

	@GetMapping("/getMaxSalaryQuery")
	public BigDecimal getMaxSalaryQuery() {

		return employeeService.getMaxSalaryQuery();
	}
	
	@GetMapping("/getMinSalaryQuery")
	public BigDecimal getMinSalaryQuery() {

		return employeeService.getMinSalaryQuery();
	}

	@GetMapping("/getTotalSalaryByDeptQuery")
	public BigDecimal getTotalSalaryByDeptQuery(@RequestParam("department") String dept) {

		return employeeService.getTotalSalaryByDeptQuery(dept);
	}

	// Returns [["IT",5],["HR",3]]
	@GetMapping("/countEmployeesPerDeptQuery")
	public List<Object[]> countEmployeesPerDeptQuery() {

		return employeeService.countEmployeesPerDeptQuery();
	}

	@GetMapping("/getDeptsWithAvgSalaryAboveQuery")
	public List<Object[]> getDeptsWithAvgSalaryAboveQuery(@RequestParam("min") BigDecimal min) {

		return employeeService.getDeptsWithAvgSalaryAboveQuery(min);
	}

//  ---------- Update / Delete ----------

//  PUT /updateEmployeeStatusQuery/5 ? status = INACTIVE
	@PatchMapping("/updateEmployeeStatusQuery/{id}")
	public ResponseEntity<String> updateEmployeeStatusQuery(@PathVariable("id") Long id,
			@RequestParam("status") EmployeeStatus status) {

		log.info("Patch /updateEmployeeStatusQuery/{} request received", id);

		String message = employeeService.updateEmployeeStatusQuery(status, id);

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	// DELETE /deleteEmployeeByStatusQuery?status=INACTIVE
	@DeleteMapping("/deleteEmployeeByStatusQuery")
	public ResponseEntity<String> deleteEmployeeByStatusQuery(@RequestParam("status") EmployeeStatus status) {

		log.warn("DELETE /deleteEmployeeByStatusQuery request received for status: {}", status);

		String message = employeeService.deleteEmployeeByStatusQuery(status);

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
//  --------------- IMPORTANT QUERY ---------------

	
//	// GET /getAllEmployeesByDeptNameJoinQuery?deptName=IT
//	@GetMapping("/getAllEmployeesByDeptNameJoinQuery")
//	public List<Employee> getAllEmployeesByDeptNameJoinQuery(@RequestParam("deptName") String deptName) {
//
//	    return employeeService.getAllEmployeesByDeptNameJoinQuery(deptName);
//	}
//
//	// GET /countEmployeesPerDeptLeftJoinQuery   -> [["IT",5],["HR",0]]
//	@GetMapping("/countEmployeesPerDeptLeftJoinQuery")
//	public List<Object[]> countEmployeesPerDeptLeftJoinQuery() {
//
//	    return employeeService.countEmployeesPerDeptLeftJoinQuery();
//	}
//
//	// GET /getHighestPaidEmployeesByDeptIdQuery?deptId=1
//	@GetMapping("/getHighestPaidEmployeesByDeptIdQuery")
//	public List<Employee> getHighestPaidEmployeesByDeptIdQuery(@RequestParam("deptId") Long deptId) {
//
//	    return employeeService.getHighestPaidEmployeesByDeptIdQuery(deptId);
//	}

}
