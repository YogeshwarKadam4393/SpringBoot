package com.empMgtSytm.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.empMgtSytm.dto.EmployeeRequestDTO;
import com.empMgtSytm.dto.EmployeeResponseDTO;
import com.empMgtSytm.dto.MultipleEmployeeRequestDTO;
import com.empMgtSytm.entity.Employee;
import com.empMgtSytm.utils.EmployeeStatus;

public interface EmployeeService {

	public Employee addEmployee(Employee employee);

	public Iterable<Employee> addAllEmployees(List<Employee> listOfEmployees);

	public boolean isEmployeeAvailable(long id);

	public String findTotalCountOfEmployees();

	public Employee getEmployeebyId(long id);

	public Iterable<Employee> getAllEmployeesByIds(List<Long> ids);

	public Iterable<Employee> getAllEmployees();

	public Iterable<Employee> sortEmployeesByfirstName();

	public Iterable<Employee> sortEmployeesBySalary();

	public Iterable<Employee> sortEmployeesByNameAndSalary();

	public Iterable<Employee> getAllEmployeesByPageAndSize(int page, int size);

	public Iterable<Employee> getAllEmployeesByPageAndSizeWithSortingByFirstName(int page, int size);

	public Iterable<Employee> getAllEmployeesByPageAndSizeWithSortingBySalary(int page, int size);

	public Employee patchEmployeeUpdate(long id, Employee employee);

	public Employee putEmployeeUpdate(long id, Employee employee);

	public String deleteEmployeeById(long id);

	public String deleteAllEmployeesByIds(List<Long> ids);

	public String deleteEmployeeByObject(long id);

	public String deleteAllEmployeeObjectsByIds(List<Long> ids);

	public String deleteAllEmployees();

	// JPA REPOSITORY METHODS

	public List<Employee> getAllEmployeesByFirstName(String firstName);

	public List<Employee> getAllEmployeesByLastName(String lastName);

	public List<Employee> getAllEmployeesBySalaryGreaterThan(BigDecimal salary);

	public List<Employee> getAllEmployeesBySalaryLessThan(BigDecimal salary);

	public List<Employee> getAllEmployeesByFirstNameLike(String ch);

	public List<Employee> getAllEmployeesByLastNameLike(String ch);

//  Whenever We are doing any Modification Operations Then Compulsory we have to give these below two annotations
	@Transactional
	@Modifying
	public String deleteEmployeeByFirstName(String firstName);

	@Transactional
	@Modifying
	public String deleteEmployeeByLastName(String lastName);

	public List<Employee> getDistinctByFirstNameAndLastName(String firstName, String lastName);

	public List<Employee> getEmployeesByFirstNameAndLastName(String firstName, String lastName);

	public List<Employee> getEmployeesByFirstNameOrLastName(String firstName, String lastName);

	public List<Employee> getEmployeeByCity(String city);

	public List<Employee> getEmployeeByDepartment(String dept);

	public List<Employee> getEmployeeByDesignation(String designation);

	public Employee getEmployeeByEmail(String email);

	public List<Employee> getEmployeesByDateOfJoiningBetween(LocalDate startingDate, LocalDate endingDate);

	public List<Employee> getAllEmployeesBySalaryLessThanEqual(BigDecimal salary);

	public List<Employee> getEmployeeByStatus(EmployeeStatus status);

	public List<Employee> getAllEmployeesBySalaryGreaterThanEqual(BigDecimal salary);

	public List<Employee> getEmployeesByDateOfJoiningAfter(LocalDate startingDate);

//  ---------- JPQL Queries ----------

	public List<Employee> getAllEmployeesByNameQuery(String firstName);

	public List<Employee> getAllEmployeesBySalaryGreaterThanQuery(BigDecimal salary);

	List<Employee> getAllEmployeesByLastNameQuery(String lastName);

	List<Employee> getAllEmployeesByCityQuery(String city);

	List<Employee> getAllEmployeesByDepartmentQuery(String dept);

	List<Employee> getAllEmployeesByStatusQuery(EmployeeStatus status);

	Employee getEmployeeByEmailQuery(String email);

//  ---------- JPQL: Comparison ----------

	List<Employee> getAllEmployeesBySalaryLessThanQuery(BigDecimal salary);

	List<Employee> getAllEmployeesBySalaryRangeQuery(BigDecimal min, BigDecimal max);

	List<Employee> getAllEmployeesByJoiningDateRangeQuery(LocalDate start, LocalDate end);

	List<Employee> getAllEmployeesJoinedAfterQuery(LocalDate date);

//  ---------- JPQL: AND / OR ----------

	List<Employee> getAllEmployeesByFullNameQuery(String firstName, String lastName);

	List<Employee> getAllEmployeesByFirstOrLastNameQuery(String firstName, String lastName);

	List<Employee> getAllEmployeesByDeptAndSalaryGreaterQuery(String dept, BigDecimal salary);

	List<Employee> getAllEmployeesByCityAndStatusQuery(String city, EmployeeStatus status);

//  ---------- JPQL: LIKE ----------

	List<Employee> getAllEmployeesByFirstNameStartsWithQuery(String prefix);

	List<Employee> getAllEmployeesByLastNameEndsWithQuery(String suffix);

	List<Employee> getAllEmployeesByFirstNameContainsQuery(String text);

	List<Employee> searchAllEmployeesByFirstNameIgnoreCaseQuery(String text);

//  ---------- JPQL: IN ----------

	List<Employee> getAllEmployeesByCitiesQuery(List<String> cities);

	List<Employee> getAllEmployeesByStatusesQuery(List<EmployeeStatus> statuses);

//  ---------- JPQL: Sorting / Top ----------

	List<Employee> getAllEmployeesOrderBySalaryDescQuery();

	List<Employee> getAllEmployeesByDeptOrderByNameQuery(String dept);

	List<Employee> getHighestPaidEmployeesQuery();

//  ---------- JPQL: Projections ----------

	List<String> getAllFirstNamesQuery();

	List<String> getDistinctCitiesQuery();

	List<Object[]> getNamesByDeptQuery(String dept);

//  ---------- JPQL: Aggregates ----------

	long countByStatusQuery(EmployeeStatus status);

	BigDecimal getAverageSalaryQuery();

	BigDecimal getMaxSalaryQuery();

	BigDecimal getMinSalaryQuery();

	BigDecimal getTotalSalaryByDeptQuery(String dept);

	List<Object[]> countEmployeesPerDeptQuery();

	List<Object[]> getDeptsWithAvgSalaryAboveQuery(BigDecimal min);

//  ---------- JPQL: Update / Delete ----------

	String updateEmployeeStatusQuery(EmployeeStatus status, Long id);

	String deleteEmployeeByStatusQuery(EmployeeStatus status);

//  --------------------- IMPORTANT QUERY ---------------

//	List<Employee> getAllEmployeesByDeptNameJoinQuery(String deptName);
//
//	List<Object[]> countEmployeesPerDeptLeftJoinQuery();
//
//	List<Employee> getHighestPaidEmployeesByDeptIdQuery(Long deptId);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                    >>>>>  DTO EMPLOYEE METHODS  <<<<
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//  Get All Employees Using DTO
	public List<EmployeeResponseDTO> getAllEmployeesDTO();

//  Get Employee By ID Using DTO
	public EmployeeResponseDTO getEmployeeByIdDTO(Long employeeId);

//  Update Employee Using DTO
	public EmployeeResponseDTO updateEmployeeByEmployeeId(Long employeeId, EmployeeRequestDTO employeeDTO);

	// Delete Employee By ID
	public void deleteEmployeeByEmployeeId(Long employeeId);

	// Add Multiple Employees to an Existing Department
	public List<EmployeeResponseDTO> addMultipleEmployeesToDepartment(MultipleEmployeeRequestDTO requestDTO);

}
