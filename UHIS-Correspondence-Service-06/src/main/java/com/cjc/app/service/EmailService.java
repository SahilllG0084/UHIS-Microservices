package com.cjc.app.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.cjc.app.dto.EmailDTO;
import com.cjc.app.enums.EmailTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String from;
	
	public void sendMail(EmailDTO edto)
	{
		log.info("Email send request received for: {}", edto.getTo());
		
		String fileName = getFileNameFromTemplateEnum(edto.getEmailTemplate());
		
		log.info("Using email template: {}", edto.getEmailTemplate());
		
		String Template = readFromTemplate(edto, fileName);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		
		try {
			helper.setFrom(from);
			helper.setTo(edto.getTo());
			if(edto.getTo() == null)
			{
				log.error("Email 'to' is null");
				throw new IllegalArgumentException("Email 'to' Is Manadatory");
			}
			
			helper.setText(Template, true);
			helper.setSubject(edto.getSubject());
			if (edto.getSubject() == null) {
				log.error("Email subject is null");
				throw new IllegalArgumentException("Email subject is mandatory");
			}
			mailSender.send(mimeMessage);
			log.info("Email sent successfully to: {}", edto.getTo());
		}
		catch (Exception e) {			
		  log.error("Failed to send email to: {}", edto.getTo(), e);
		  throw new IllegalArgumentException("Failed To Send Email", e);
		}
	}

	private String readFromTemplate(EmailDTO edto, String fileName) {
		
		log.info("Reading email template file: {}", fileName);
		
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);

			StringBuilder sb = new StringBuilder();
			String msg;

			while ((msg = br.readLine()) != null) {
				sb.append(msg);
			}

			String template = sb.toString();

			switch (edto.getEmailTemplate()) {

			case CASEWORKER_REGISTRATION:
				log.info("Preparing CASEWORKER_REGISTRATION template");
				template = template.replace("{{FULL_NAME}}", safe(edto.getFullName()));
				template = template.replace("{{USER_NAME}}", safe(edto.getEmailId()));
				template = template.replace("{{PASS_WORD}}", safe(edto.getPassword()));
				template = template.replace("{{PHONE_NO}}", safe(edto.getMobileNo()));
				template = template.replace("{{REGISTERED_DATE}}",safe(String.valueOf(edto.getCreateDate())));
				break;

			case FORGOT_PASSWORD:
				log.info("Preparing FORGOT_PASSWORD template");
				template = template.replace("{{FULL_NAME}}", safe(edto.getFullName()));
				template = template.replace("{{NEW_PASSWORD}}", safe(edto.getPassword()));
				template = template.replace("{{USER_NAME}}",safe(edto.getEmailId()));
				break;

			case LOGIN_ALERT:
				log.info("Preparing LOGIN_ALERT template");
				template = template.replace("{{FULL_NAME}}", safe(edto.getFullName()));
				template = template.replace("{{USER_NAME}}", safe(edto.getEmailId()));
				break;
			}

			return template;

		} catch (IOException e) {
			log.error("Error reading email template file: {}", fileName, e);
			throw new RuntimeException("Error reading email template", e);
		}
	}
	
	private String safe(String value) {
		return value == null ? "" : value;
	}

	private String getFileNameFromTemplateEnum(EmailTemplate template) {
		
		log.info("Resolving file for email template: {}", template);
		
		switch (template) {
		case CASEWORKER_REGISTRATION:
			return "caseworker-registration.txt";

		case FORGOT_PASSWORD:
			return "forgot-password.txt";

		case LOGIN_ALERT:
			return "login-alert.txt";

		default:
			log.error("Unknown email template: {}", template);
			throw new RuntimeException("Unknown template: " + template);
		}
		
	}
}
