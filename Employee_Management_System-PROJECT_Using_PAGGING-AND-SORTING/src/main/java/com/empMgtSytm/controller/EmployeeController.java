package com.empMgtSytm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.empMgtSytm.entity.Employee;
import com.empMgtSytm.repo.EmployeeRepo;
import com.empMgtSytm.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "Employee Controller", description = "This Controller is Responsible For Create , Fetch , Update , Delete  Employees")
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

}
