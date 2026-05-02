package com.cjc.app.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@DynamicUpdate
@AllArgsConstructor
@Entity
@Table(name = "kids_details")
public class KidsDetails {
     
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
