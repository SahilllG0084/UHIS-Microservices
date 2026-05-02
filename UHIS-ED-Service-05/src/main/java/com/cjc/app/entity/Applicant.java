package com.cjc.app.entity;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;

@Data
public class Applicant {
   
    private Long ssnNo;
    
    private String caseNumber;
    
	private String fullName;
		
	private String email;
	
	private Long mobileNo;
	
	private LocalDate dob;
	
	private String gender;
	
	private Integer age;
	
	private String stateName;
	
	private String country;

	@OneToOne(cascade = CascadeType.ALL)
	private Education education;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Income income;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Kids> kidsdetails;
	
	@OneToOne(cascade = CascadeType.ALL)
	private PlanSelection planSelection;
}
