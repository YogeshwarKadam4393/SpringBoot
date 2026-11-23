package com.empMgtSytm.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.empMgtSytm.entity.Employee;

@Service
public class EmailService {

	// Step 1: Inject JavaMailSender and TemplateEngine Dependencies

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TemplateEngine templateEngine;

	// Step 2: Create Method to Send Dynamic Welcome Email

	public void sendWelcomeEmail(Employee employee) {

		try {

			// Step 3: Create MimeMessage Object
			// MimeMessage represents the actual email message.
			// It supports HTML content, attachments, CC, BCC, etc.

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			// Step 4: Create MimeMessageHelper
			// MimeMessageHelper simplifies email configuration.
			// true enables multipart support.
			// UTF-8 handles special characters and different languages.

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			// Step 5: Set Email Details
			// Set receiver email address and email subject.

			helper.setTo(employee.getEmail());

			helper.setSubject("Google Hiring - Welcome to Our Organization");

			// Step 6: Create Thymeleaf Context
			// Context is used to pass dynamic employee data to the HTML template.

			Context context = new Context();

			// Step 7: Add Employee Details to Thymeleaf Context
			// setVariable() stores dynamic values that can be accessed in HTML
			// using Thymeleaf expressions such as ${employeeName}.

			context.setVariable("employeeName", employee.getFirstName() + " " + employee.getLastName());

			context.setVariable("employeeEmail", employee.getEmail());

			context.setVariable("designation", employee.getDesignation());

			context.setVariable("salary", employee.getSalary());

			// Step 8: Process HTML Template Using TemplateEngine
			// TemplateEngine loads welcome-email.html from the templates folder.
			// It replaces Thymeleaf expressions with actual employee data.
			// Finally, it generates dynamic HTML content as a String.

			String emailContent = templateEngine.process("welcome-email", context);

			// Step 9: Set Generated HTML Content in MimeMessage
			// First argument represents the generated HTML content.
			// Second argument true indicates that the content is HTML.

			helper.setText(emailContent, true);

			// Step 10: Send Email Using JavaMailSender
			// JavaMailSender sends the configured MimeMessage
			// through the SMTP server.

			javaMailSender.send(mimeMessage);

			System.out.println("Welcome Email Sent Successfully!");

		} catch (Exception e) {

			// Step 11: Handle Email Sending Exceptions
			// Print the original exception for debugging.
			// Throw RuntimeException if email processing or sending fails.

			e.printStackTrace();

			throw new RuntimeException("Unable To Send Welcome Email", e);

		}

	}

}