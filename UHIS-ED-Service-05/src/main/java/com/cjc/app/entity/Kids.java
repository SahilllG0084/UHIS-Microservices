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
public class Kids{
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long kidsId;
	
	private String caseNumber;
	
	private String name;
	
	private Integer age;
	
	private String gender;
	
	private LocalDate dob;
	
	private String ssnNo;
}
