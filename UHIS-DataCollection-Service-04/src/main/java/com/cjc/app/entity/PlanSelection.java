package com.cjc.app.entity;

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
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "plan_selection")
public class PlanSelection {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer psid;
	
	private String planName;
	
	private String category;
	
	private String caseNumber;
}
