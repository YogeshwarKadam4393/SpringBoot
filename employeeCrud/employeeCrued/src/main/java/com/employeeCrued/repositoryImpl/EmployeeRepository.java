package com.employeeCrued.repositoryImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.employeeCrued.modal.Employee;
import com.employeeCrued.repository.EmployeeRepositoryServices;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class EmployeeRepository implements EmployeeRepositoryServices {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Employee registor(Employee employee) {
		log.info("request recive repo layer", employee);
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(employee);
		tx.commit();
		return employee;
	}

	@Override
	public Employee getEmployeeDetails(int id) {
		log.info("Request recive repo layer to fetch employee details useing id : {} ",id);
		Session session = sessionFactory.openSession();
		Employee emp = session.get(Employee.class, id);
		if (emp == null) {
			log.info("check employee is null or not");
			throw new RuntimeException("Employee not found this id " + id+" Enter appropate id");
		}
		log.info("Send responce in controller");
		return emp;
	}

}
