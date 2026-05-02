package com.cjc.app.dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CWResponseDto {
  
	private String branchName;
	
	private String fullName;
	
	private String emailId;
	
	private Long phoneNo;
	
	private LocalDate birthDate;
	
	private String gender;
	
	private LocalDate createDate;
	
	private LocalDate updateDate;
	
    private String status;
}
