package com.cjc.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EligibilityRequestDTO {
   
	@NotBlank(message = "SSN Number Is Required")
	private Integer ssnNo;
    
	@NotBlank(message = "Case Number Is Required")
	private String caseNumber;

	@NotBlank(message = "PlanName Is Required")
	private String planName;

	@NotBlank(message = "Plan Status Is Required")
	private String planStatus;

	@NotBlank(message = "Full Name Is Required")
	private String fullName;

	@NotBlank(message = "BenefitAmt Is Required")
	private Double benefitAmt;

	@NotBlank(message = "Denial Reason Is Required")
	private String denialReason;

	@NotBlank(message = "Start Date Is Required")
	private LocalDate startDate;

	@NotBlank(message = "End Date Is Required")
	private LocalDate endDate;
}
