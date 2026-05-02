package com.cjc.app.dto;

import lombok.Data;

@Data
public class EligibilityResponseDTO {
    
	private String caseNumber;

	private String planName;

	private String planStatus;

	private String fullName;

	private Double benefitAmt;

	private String denialReason;
}
