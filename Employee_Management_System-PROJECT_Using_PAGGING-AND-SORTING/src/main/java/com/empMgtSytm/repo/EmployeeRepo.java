package com.empMgtSytm.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.empMgtSytm.entity.Employee;

// CrudRepository<Employee, Long> --->
// What is CrudRepository? ---> An interface from Spring Data that already contains the basic CRUD operations
// Employee	---> The entity this repository manages ---> Your Employee class
// Long	---> The type of the primary key ---> The @Id ( private Long id ) field

@Repository
public interface EmployeeRepo extends PagingAndSortingRepository<Employee, Long> {
	
//  existsByEmail
//	  │      │
//	  │      └── property: "email" (must match the field name in Employee)
//	  └── action: check if a record exists	
	
    boolean existsByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);
    
}