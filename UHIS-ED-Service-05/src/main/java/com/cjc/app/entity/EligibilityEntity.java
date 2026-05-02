package com.cjc.app.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EligibilityEntity {
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer edId;
	
	private Long ssnNo;

	private String caseNumber;

	private String planName;

	private String planStatus;

	private String fullName;

	private Double benefitAmt;

	private String denialReason;

	private LocalDate startDate;

	private LocalDate endDate;
}
