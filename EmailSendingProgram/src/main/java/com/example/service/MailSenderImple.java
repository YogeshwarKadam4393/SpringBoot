package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderImple implements MailSender{

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendMail() {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
//		message.setFrom("ak9976999@gmail.com");
		message.setTo("yogeshwarKadam0@gmail.com");
		message.setSubject("Google Hiring");
		message.setText("Hey Mr.Yogesh You are Hired..."
				      + "Thank You...");
		
		javaMailSender.send(message);
		
	}

}
