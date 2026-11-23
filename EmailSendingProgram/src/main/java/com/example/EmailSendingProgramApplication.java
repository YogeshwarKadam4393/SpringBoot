package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.service.MailSender;

@SpringBootApplication
public class EmailSendingProgramApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(EmailSendingProgramApplication.class, args);
	
		MailSender mail = run.getBean(MailSender.class);
		mail.sendMail();
	}

}
