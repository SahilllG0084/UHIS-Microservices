package com.cjc.app.dtos;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CWRequestDto {
	
	@NotBlank(message = "Branch name is required")
	private String branchName;
    
	@NotBlank(message = "Full name is required")
	private String fullName;
	
	@Email(message = "Invalid email format")
	@Column(unique = true)
	private String emailId;
	
	@NotNull
	@Pattern(regexp = "^[7-9]\\d{9}$", message = "Mobile number must be 10 digits and start with 7, 8, or 9")
	private Long phoneNo;
	
	@Past(message = "Birthdate must be in past")
	private LocalDate birthDate;
	
	@NotBlank
	private String gender;
	
	private LocalDate createDate;
	
	private LocalDate updateDate;
	
	private String status;
}
