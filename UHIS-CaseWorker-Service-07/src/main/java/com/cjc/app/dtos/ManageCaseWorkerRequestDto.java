package com.cjc.app.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ManageCaseWorkerRequestDto {
   
	@NotBlank(message = "Full Name is required")
	private String fullName;
	
	@NotBlank(message = "Phone Number is must")
	private String phoneNo;
	
	@Email(message = "Email is required")
	private String emailId;
	
	@NotBlank(message = "Branch name is required")
	private String branchName;
	
	@NotBlank
	private String status;
}
