package com.cjc.app.dto;

import java.time.LocalDate;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class CitizenRequestDTO {
  
    @NotNull(message = "SSN is required")
    @Digits(integer = 9, fraction = 0, message = "SSN must be 9 digits")
    private Long ssnNo;
	
    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 50, message = "Full name must be 3–50 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Mobile number is required")
    @Digits(integer = 10, fraction = 0, message = "Mobile number must be 10 digits")
    private Long mobileNo;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String gender;

    @NotNull(message = "Date of birth is required")
    @Past(message = "DOB must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    
    @NotNull(message = "Age is required")
    private Integer age;
}
