package com.cjc.app.dtos;

import java.time.LocalDate;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class AppRequestDTO {
    
	    @NotBlank(message = "Case Number is required")
	    private String caseNumber;

	    @NotBlank(message = "Full Name is required")
	    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters")
	    private String fullName;

	    @NotBlank(message = "Email is required")
	    @Email(message = "Invalid Email format")
	    private String email;

	    @NotNull(message = "Mobile Number is required")
	    private String mobileNo;

	    @NotBlank(message = "Gender is required")
	    @Pattern(regexp = "Male|Female|Other", message = "Invalid Gender")
	    private String gender;

	    @NotNull(message = "Age is required")
	    @Min(value = 0, message = "Age cannot be negative")
	    @Max(value = 120, message = "Invalid Age")
	    private Integer age;

	    @NotNull(message = "Date of Birth is required")
	    @Past(message = "DOB must be in the past")
	    @JsonFormat(pattern = "yyyy-MM-dd")
	    private LocalDate dob;

	    @NotNull(message = "SSN Number is required")
	    @Digits(integer = 9, fraction = 0, message = "SSN must be 9 digits")
	    private Long ssnNo;

	    @NotBlank(message = "State is required")
	    private String stateName;

	    @NotBlank(message = "Country is required")
	    @Pattern(regexp = "India", message = "Invalid Country")
	    private String country;
}
