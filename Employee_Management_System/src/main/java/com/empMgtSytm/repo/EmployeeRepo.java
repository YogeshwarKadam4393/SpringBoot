package com.empMgtSytm.repo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.empMgtSytm.entity.Employee;
import com.empMgtSytm.utils.EmployeeStatus;

// Employee	---> The entity this repository manages ---> Your Employee class
// Long	---> The type of the primary key ---> The @Id ( private Long id ) field

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	boolean existsByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);

	public List<Employee> findAllEmployeesByFirstName(String firstName);

	public List<Employee> findAllEmployeesByLastName(String lastName);

	public List<Employee> findAllEmployeesBySalaryGreaterThan(BigDecimal salary);
	
	public List<Employee> findAllEmployeesBySalaryLessThan(BigDecimal salary);

	// select * from Employee where firstName like 'Y %';
	public List<Employee> findAllEmployeesByFirstNameLike(String ch);

	// select * from Employee where lastName like 'Y %';
	public List<Employee> findAllEmployeesByLastNameLike(String ch);

	public void deleteEmployeeByFirstName(String firstName);

	public void deleteEmployeeByLastName(String lastName);

//  select DISTINCT firstName,lastName from Employee
	public List<Employee> findDistinctByFirstNameAndLastName(String firstName, String lastName);

	public List<Employee> findEmployeesByFirstNameAndLastName(String firstName, String lastName);

	public List<Employee> findEmployeesByFirstNameOrLastName(String firstName, String lastName);

	public List<Employee> findByCity(String city);

	public List<Employee> findByDepartmentIs(String department);

	public List<Employee> findByDesignationEquals(String designation);

	public List<Employee> findByStatus(EmployeeStatus status);

	public Optional<Employee> findByEmail(String email);
	
	public List<Employee> findEmployeesByDateOfJoiningBetween(LocalDate startingDate , LocalDate endingDate);

	public List<Employee> findAllEmployeesBySalaryLessThanEqual(BigDecimal salary);

	public List<Employee> findAllEmployeesBySalaryGreaterThanEqual(BigDecimal salary);

	public List<Employee> findEmployeesByDateOfJoiningAfter(LocalDate startingDate);
	
	// @Param is Used here to send firstName to PlaceHolder from Postman 
	@Query(value = "SELECT e FROM Employee e WHERE e.firstName = :firstName")
	public List<Employee> getAllEmployeesByNameQuery(@Param ("firstName") String firstName);
	
	// Find Employee By Salary Greater Than Given salary
	@Query(value = "SELECT e FROM Employee e WHERE e.salary > :salary")
	public List<Employee> getAllEmployeesBySalaryGreaterThanQuery(@Param ("salary") BigDecimal salary);

	@Query("SELECT e FROM Employee e WHERE e.lastName = :lastName")
	List<Employee> getByAllEmployeesByLastNameQuery(@Param("lastName") String lastName);

	@Query("SELECT e FROM Employee e WHERE e.city = :city")
	List<Employee> getAllEmployeesByCityQuery(@Param("city") String city);

	@Query("SELECT e FROM Employee e WHERE e.department = :dept")
	List<Employee> getAllEmployeesByDepartmentQuery(@Param("dept") String dept);

	@Query("SELECT e FROM Employee e WHERE e.status = :status")
	List<Employee> getAllEmployeesByStatusQuery(@Param("status") EmployeeStatus status);

//   0 or 1 result → Use Optional<Employee>
//	 0 to many results → Use List<Employee> 
	@Query("SELECT e FROM Employee e WHERE e.email = :email")
	Optional<Employee> getEmployeeByEmailQuery(@Param("email") String email);

	// ---------- Comparison ----------

	@Query("SELECT e FROM Employee e WHERE e.salary < :salary")
	List<Employee> getAllEmployeesBySalaryLessThanQuery(@Param("salary") BigDecimal salary);

	@Query("SELECT e FROM Employee e WHERE e.salary BETWEEN :min AND :max")
	List<Employee> getAllEmployeesBySalaryRangeQuery(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

	@Query("SELECT e FROM Employee e WHERE e.dateOfJoining BETWEEN :start AND :end")
	List<Employee> getAllEmployeesByJoiningDateRangeQuery(@Param("start") LocalDate start, @Param("end") LocalDate end);

	@Query("SELECT e FROM Employee e WHERE e.dateOfJoining > :date")
	List<Employee> getAllEmployeesJoinedAfterQuery(@Param("date") LocalDate date);

	// ---------- AND / OR ----------

	@Query("SELECT e FROM Employee e WHERE e.firstName = :firstName AND e.lastName = :lastName")
	List<Employee> getAllEmployeesByFullNameQuery(@Param("firstName") String firstName, @Param("lastName") String lastName);

	@Query("SELECT e FROM Employee e WHERE e.firstName = :firstName OR e.lastName = :lastName")
	List<Employee> getAllEmployeesByFirstOrLastNameQuery(@Param("firstName") String firstName, @Param("lastName") String lastName);

	@Query("SELECT e FROM Employee e WHERE e.department = :dept AND e.salary > :salary")
	List<Employee> getAllEmployeesByDeptAndSalaryGreaterQuery(@Param("dept") String dept, @Param("salary") BigDecimal salary);

	@Query("SELECT e FROM Employee e WHERE e.city = :city AND e.status = :status")
	List<Employee> getAllEmployeesByCityAndStatusQuery(@Param("city") String city, @Param("status") EmployeeStatus status);

	// ---------- LIKE ----------

	@Query("SELECT e FROM Employee e WHERE e.firstName LIKE :prefix%")
	List<Employee> getAllEmployeesByFirstNameStartsWithQuery(@Param("prefix") String prefix);
	
	@Query("SELECT e FROM Employee e WHERE e.lastName LIKE %:suffix")
	List<Employee> getAllEmployeesByLastNameEndsWithQuery(@Param("suffix") String suffix);

//  Contains : This finds all employees whose first   name contains the given text anywhere. 
	@Query("SELECT e FROM Employee e WHERE e.firstName LIKE %:text%")
	List<Employee> getAllEmployeesByFirstNameContainsQuery(@Param("text") String text);

	
	@Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :text, '%'))")
	List<Employee> searchAllEmployeesByFirstNameIgnoreCaseQuery(@Param("text") String text);

	// ---------- IN ----------

	@Query("SELECT e FROM Employee e WHERE e.city IN :cities")
	List<Employee> getAllEmployeesByCitiesQuery(@Param("cities") List<String> cities);

	@Query("SELECT e FROM Employee e WHERE e.status IN :statuses")
	List<Employee> getAllEmployeesByStatusesQuery(@Param("statuses") List<EmployeeStatus> statuses);

	// ---------- Sorting / Top ----------

	@Query("SELECT e FROM Employee e ORDER BY e.salary DESC")
	List<Employee> getAllEmployeesAllOrderBySalaryDescQuery();

	@Query("SELECT e FROM Employee e WHERE e.department = :dept ORDER BY e.firstName ASC")
	List<Employee> getAllEmployeesByDeptOrderByNameQuery(@Param("dept") String dept);

	@Query("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e2.salary) FROM Employee e2)")
	List<Employee> getHighestPaidEmployeesQuery();

	// ---------- Projections (specific fields) ----------

	@Query("SELECT e.firstName FROM Employee e")
	List<String> getAllFirstNamesQuery();

	@Query("SELECT DISTINCT e.city FROM Employee e")
	List<String> getDistinctCitiesQuery();

	@Query("SELECT e.firstName, e.lastName FROM Employee e WHERE e.department = :dept")
	List<Object[]> getNamesByDeptQuery(@Param("dept") String dept);

	// ---------- Aggregates ----------

	@Query("SELECT COUNT(e) FROM Employee e WHERE e.status = :status")
	long countByStatusQuery(@Param("status") EmployeeStatus status);

	@Query("SELECT AVG(e.salary) FROM Employee e")
	BigDecimal getAverageSalaryQuery();

	@Query("SELECT MAX(e.salary) FROM Employee e")
	BigDecimal getMaxSalaryQuery();
	
	@Query("SELECT MIN(e.salary) FROM Employee e")
	BigDecimal getMinSalaryQuery();

	@Query("SELECT SUM(e.salary) FROM Employee e WHERE e.department = :dept")
	BigDecimal getTotalSalaryByDeptQuery(@Param("dept") String dept);

	@Query("SELECT e.department, COUNT(e) FROM Employee e GROUP BY e.department")
	List<Object[]> countEmployeesPerDeptQuery();

//  GROUP BY e.department makes groups: IT, HR, Sales.
//	AVG(e.salary) is calculated per group: IT = 70000, HR = 35000, Sales = 55000.
//	HAVING AVG(e.salary) > 50000 removes HR, because 35000 is not above 50000.
	
//  Rule of thumb ---> 
//	Function	            --->       Hibernate 5 result type
//	AVG	                    --->       Double
//	SUM of BigDecimal       --->       BigDecimal
//	MAX / MIN of BigDecimal --->       BigDecimal
//	COUNT	Long
	
	@Query("SELECT e.department, AVG(e.salary) FROM Employee e GROUP BY e.department HAVING AVG(e.salary) > :min")
	List<Object[]> getDeptsWithAvgSalaryAboveQuery(@Param("min") Double min);
	
	
//  ---------- Update / Delete (need @Modifying + @Transactional) ----------

 	
	@Modifying
	@Transactional
	@Query("UPDATE Employee e SET e.status = :status WHERE e.id = :id")
	int updateEmployeeStatusQuery(@Param("status") EmployeeStatus status, @Param("id") Long id);

	@Modifying
	@Transactional
	@Query("DELETE FROM Employee e WHERE e.status = :status")
	int deleteEmployeeByStatusQuery(@Param("status") EmployeeStatus status);
	
//  ----------- IMPORTANT QUERY ---------------
	
//	// Query 1: employees of a department, by department name
//	@Query("SELECT e FROM Employee e JOIN e.department d WHERE d.name = :deptName")
//	List<Employee> getAllEmployeesByDeptNameJoinQuery(@Param("deptName") String deptName);
//
//	// Query 2: employee count per department (includes departments with 0 employees)
//	@Query("SELECT d.name, COUNT(e) FROM Department d LEFT JOIN d.employees e GROUP BY d.name")
//	List<Object[]> countEmployeesPerDeptLeftJoinQuery();
//
//	// Query 3: highest paid employee(s) in a department
//	@Query("SELECT e FROM Employee e WHERE e.department.id = :deptId "
//	     + "AND e.salary = (SELECT MAX(e2.salary) FROM Employee e2 WHERE e2.department.id = :deptId)")
//	List<Employee> getHighestPaidEmployeesByDeptIdQuery(@Param("deptId") Long deptId);
	

}