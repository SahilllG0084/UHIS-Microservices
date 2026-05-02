package com.cjc.app.entity;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.DynamicUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
public class Applicant{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer applicantId;
	
	private Long ssnNo;
	
	private String fullName;
	
	private String caseNumber;
	
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
	private List<KidsDetails> kidsdetails;
	
	@OneToOne(cascade = CascadeType.ALL)
	private PlanSelection planSelection;
}
