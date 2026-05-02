package com.cjc.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cjc.app.dto.EmailDTO;
import com.cjc.app.service.EmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/crd/module")
public class EmailController {
    
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/mails")
	public ResponseEntity<String> sendMail(@RequestBody EmailDTO edto) {

	    log.info("POST /mails - Email API called for: {}", edto.getTo());

	    emailService.sendMail(edto);

	    log.info("POST /mails - Email sent successfully to: {}", edto.getTo());

	    return ResponseEntity.ok("Email Sent Successfully...");
	}
}
