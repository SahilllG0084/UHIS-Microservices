package com.cjc.app.dtos;

import java.time.LocalDate;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CWRequestDto {

	    @NotNull(message = "Account ID is required")
	    private Long accountId;

	    @NotBlank(message = "Full name is required")
	    @Size(min = 2, max = 100, message = "Full name must be 2–100 characters")
	    private String fullName;

	    @NotBlank(message = "Email is required")
	    @Email(message = "Email should be valid")
	    private String emailId;

	    @NotNull(message = "Phone number is required")
	    @Digits(integer = 10, fraction = 0, message = "Phone number must be numeric and up to 10 digits")
	    private Long phoneNo;

	    @NotNull(message = "Birth date is required")
	    @Past(message = "Birth date must be in the past")
	    private LocalDate birthDate;

	    @NotBlank(message = "Gender is required")
	    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
	    private String gender;

	    @NotNull(message = "SSN number is required")
	    @Digits(integer = 9, fraction = 0, message = "SSN number must be 9 digits")
	    private Long ssnNo;

	    @NotNull(message = "Create date is required")
	    @PastOrPresent(message = "Create date cannot be in the future")
	    private LocalDate createDate;

	    @NotNull(message = "Update date is required")
	    @PastOrPresent(message = "Update date cannot be in the future")
	    private LocalDate updateDate;
}
