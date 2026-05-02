package com.cjc.app.dto;

import java.time.LocalDate;
import com.cjc.app.enums.EmailTemplate;
import lombok.Data;

@Data
public class EmailDTO {

	private EmailTemplate emailTemplate;

	private String to;

	private String from;

	private String subject;

	private String text;

	private String fullName;
	
	private String branchName;

	private String gender;

	private Integer age;

	private LocalDate dob;
	
	private LocalDate birthDate;

	private String mobileNo;

	private String emailId;

	private String password;

	private String status;

	private Long ssnNo;

	private String planName;

	private String category;
	
	private String eligibility;

	private LocalDate startDate;

	private LocalDate endDate;
	
	private LocalDate createDate;
	
	private LocalDate updateDate;
}
