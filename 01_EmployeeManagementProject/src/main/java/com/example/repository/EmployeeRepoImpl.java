package com.example.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.entity.Employee;

@Repository
public class EmployeeRepoImpl implements EmployeeRepo{

	@Autowired
	public SessionFactory sf;
	
	@Override
	public void addEmployee(Employee employee) {
		
	Session session = sf.openSession();	
	
	Transaction tx = session.beginTransaction();
	
	session.save(employee);
	
	tx.commit();
	
	System.out.println("Employee Saved Successfully...");
		
	}

	@Override
	public List<Employee> checkEmpAvailability() {

	    Session session = sf.openSession();

	    Query query = session.createQuery("from Employee");

	    List<Employee> resultList = query.getResultList();

	    return resultList;
	}
	
	@Override
	public List<Employee> getAllEmployees() {

	    Session session = sf.openSession();

	    Query query = session.createQuery("from Employee");

	    List<Employee> resultList = query.getResultList();

	    return resultList;
	}

}
