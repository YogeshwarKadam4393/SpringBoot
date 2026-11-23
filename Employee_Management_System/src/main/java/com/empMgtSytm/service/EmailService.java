package com.empMgtSytm.service;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.empMgtSytm.entity.Employee;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TemplateEngine templateEngine;

	// ==================================================
	// METHOD 1: SEND WELCOME EMAIL WITHOUT ATTACHMENTS
	// ==================================================

	public void sendWelcomeEmail(Employee employee) {

		log.info("Initiating welcome email process without attachments");

		sendWelcomeEmail(employee, null);

	}

	// ==================================================
	// METHOD 2: SEND WELCOME EMAIL WITH ATTACHMENTS
	// ==================================================

	public void sendWelcomeEmail(Employee employee, List<MultipartFile> attachments) {

		log.info("Initiating welcome email process");

		try {

			// STEP 1: Validate Employee

			if (employee == null) {

				log.warn("Cannot send welcome email because employee object is null");

				throw new IllegalArgumentException("Employee Cannot Be Null");
			}

			// STEP 2: Create MimeMessage object

			log.debug("Creating MimeMessage object");

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			// STEP 3: Create MimeMessageHelper

			log.debug("Creating MimeMessageHelper");

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			// STEP 4: Configure email recipient

			helper.setTo(employee.getEmail());

			// STEP 5: Configure email subject

			helper.setSubject("Google Hiring - Welcome to Our Organization");

			log.debug("Email recipient and subject configured");

			// STEP 6: Create Thymeleaf Context

			Context context = new Context();

			context.setVariable("employeeName", employee.getFirstName());

			context.setVariable("employeeEmail", employee.getEmail());

			context.setVariable("designation", employee.getDesignation());

			context.setVariable("salary", employee.getSalary());

			context.setVariable("city", employee.getCity());

			log.debug("Thymeleaf context variables configured");

			// STEP 7: Process HTML Template

			String emailContent = templateEngine.process("welcome-email", context);

			log.debug("Welcome email HTML template processed successfully");

			// STEP 8: Prepare Plain Text Content

			String plainText = "Welcome to our organization, " + employee.getFirstName()
					+ ". We are happy to have you on our team.";

			// STEP 9: Set Email Content

			helper.setText(plainText, emailContent);

			log.debug("Plain text and HTML email content configured");

			// STEP 10: Add Attachments

			if (attachments != null && !attachments.isEmpty()) {

				log.info("Processing {} email attachments", attachments.size());

				int attachmentCount = 0;

				for (MultipartFile attachment : attachments) {

					// Check whether attachment is null

					if (attachment == null) {

						log.warn("Skipping null attachment");

						continue;
					}

					// Check whether attachment is empty

					if (attachment.isEmpty()) {

						log.warn("Skipping empty attachment");

						continue;
					}

					// Get original file name

					String originalFileName = attachment.getOriginalFilename();

					String fileName = "attachment";

					if (originalFileName != null && !originalFileName.trim().isEmpty()) {

						String cleanedFileName = StringUtils.cleanPath(originalFileName);

						cleanedFileName = cleanedFileName.replace("\\", "/");

						int lastIndex = cleanedFileName.lastIndexOf("/");

						if (lastIndex >= 0) {

							fileName = cleanedFileName.substring(lastIndex + 1);

						} else {

							fileName = cleanedFileName;
						}
					}

					// Get attachment content type

					String contentType = attachment.getContentType();

					if (contentType == null || contentType.trim().isEmpty()) {

						contentType = "application/octet-stream";
					}

					// Add attachment to email

					InputStreamSource inputStreamSource = attachment;

					helper.addAttachment(fileName, inputStreamSource, contentType);

					attachmentCount++;

					log.debug("Attachment added successfully: {}", fileName);
				}

				log.info("Total valid attachments added: {}", attachmentCount);

			} else {

				log.debug("No attachments provided for welcome email");
			}

			// STEP 11: Send Email

			log.info("Sending welcome email");

			javaMailSender.send(mimeMessage);

			log.info("Welcome email sent successfully");

		} catch (Exception e) {

			log.error("Failed to send welcome email: {}", e.getMessage(), e);

			throw new RuntimeException("Unable To Send Welcome Email", e);
		}
	}
}
