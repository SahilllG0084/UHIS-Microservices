package com.cjc.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "PLAN_DTLS")
public class PlanSelection {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer psid;
	
	private String planName;
	
	private String category;
	
	private String caseNumber;
}
