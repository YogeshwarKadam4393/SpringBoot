package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		
		// This is used to get count of beans present in the application
		System.out.println(context.getBeanDefinitionCount());

		// This is print all Bean names present in the application
        String[] beans = context.getBeanDefinitionNames();  

        for (String bean : beans) {
            System.out.println(bean);
        }
        
        
        
	}

}
