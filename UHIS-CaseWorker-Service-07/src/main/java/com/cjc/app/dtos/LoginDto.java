package com.cjc.app.dtos;

import javax.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginDto {
    
	@Email(message = "Email Is Required...!")
	private String emailId;
	
	
	private String password;
}
