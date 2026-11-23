package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entity.Employee;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		// This is created with @Builder Design pattern
		Employee employee = Employee.builder().id(101).name("Yogesh").salary(100000000.000).build();
		System.out.println(employee);

		// This is Created using Our normal way Where we have used @Setter @Getter
		// @ToString @EqualAndHashcode @AllArgsContructor
		Employee emp = new Employee();
		emp.setId(101);
		emp.setName("Yogesh");
		emp.setSalary(100000000.000);

		System.out.println(emp.getId());
		System.out.println(emp.getName());
		System.out.println(emp.getSalary());

		System.out.println(emp);
		
		System.out.println(employee.equals(emp));

	}

}
