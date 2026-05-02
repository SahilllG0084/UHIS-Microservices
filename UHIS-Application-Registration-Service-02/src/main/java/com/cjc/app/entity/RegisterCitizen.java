package com.cjc.app.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicUpdate;
import com.cjc.app.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@DynamicUpdate
@AllArgsConstructor
@Entity
public class RegisterCitizen {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

	@Column(unique = true, nullable = false)
	private String caseNumber;
	
    private String fullName;
    
    private String email;
    
    private Long mobileNo;
    
    private String gender;
    
    private Integer age;
    
    private LocalDate dob;
    
    private Long ssnNo;

    @Enumerated(EnumType.STRING)
    private State stateName;

    private String country;
}
