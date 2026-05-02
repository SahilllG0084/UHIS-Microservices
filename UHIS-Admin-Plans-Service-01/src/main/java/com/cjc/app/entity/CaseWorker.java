package com.cjc.app.entity;

import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import com.cjc.app.enums.Status;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity 
public class CaseWorker {
    
	@Id
	@SequenceGenerator(name = "admin", initialValue = 101, sequenceName = "adminid", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin")
	private Integer adminId;
	
	private Long accountId;
	
	private String fullName;
	
	private String emailId;
	
	private String password;
	
	private Long phoneNo;
	
	private LocalDate birthDate;
	
	private String gender;
	
	private Long ssnNo;
		
	private LocalDate createDate;
	
	private LocalDate updateDate;
	
	@Enumerated(EnumType.STRING)
	private Status status; 
}
