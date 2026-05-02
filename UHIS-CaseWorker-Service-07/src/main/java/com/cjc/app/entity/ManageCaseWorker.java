package com.cjc.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Caseworker_Manager")
public class ManageCaseWorker {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer CaseWorkerId;
	
	private String branchName;
	
    private String fullName;
	
	private String emailId;
	
	private String password;
	
	private String phoneNo;
	
	private String status;
}
