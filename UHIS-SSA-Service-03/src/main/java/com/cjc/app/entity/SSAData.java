package com.cjc.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SSAData {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sid;
	
	private String fullname;
	private String ssano;
	private String state;
	private String country;
}
