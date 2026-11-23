package com.empMgtSytm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empMgtSytm.entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {

    public Department findDepartmentByDepartmentName(String departmentName);

}

