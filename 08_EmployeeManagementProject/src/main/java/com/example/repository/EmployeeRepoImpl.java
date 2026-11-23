package com.example.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.entity.Employee;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class EmployeeRepoImpl implements EmployeeRepo{

	@Autowired
	public SessionFactory sf;
	
	@Override
	public void addEmployee(Employee employee) {

	    Session session = null;
	    Transaction tx = null;

	    try {

	        log.debug("Opening Hibernate session");

	        session = sf.openSession();

	        log.debug("Starting database transaction");

	        tx = session.beginTransaction();

	        log.info("Saving employee to database");

	        session.save(employee);

	        tx.commit();

	        log.info("Employee saved successfully");

	    } catch (Exception e) {

	        log.error("Failed to save employee", e);

	        if (tx != null) {
	            tx.rollback();
	        }

	    } finally {

	        if (session != null) {
	            session.close();
	        }

	        log.debug("Hibernate session closed");
	    }
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
