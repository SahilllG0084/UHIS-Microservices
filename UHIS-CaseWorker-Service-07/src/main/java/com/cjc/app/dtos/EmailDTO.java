package com.cjc.app.dtos;

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
	
	private String phoneNo;
	
	private String emailId;
	
	private String status;
	
	private String password;
	
	private LocalDate createDate;
}
