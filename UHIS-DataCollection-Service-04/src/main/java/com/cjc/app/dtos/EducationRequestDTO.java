package com.cjc.app.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EducationRequestDTO {

	@NotBlank(message = "Degree is required")
	private String highestDegree;

	@NotBlank(message = "Case number is required")
	private String caseNumber;

	@NotBlank(message = "Graduation status is required")
	@Pattern(regexp = "Graduated|Undergraduate|Postgraduate|Not Graduated", message = "Graduation status must be one of: Graduated, Undergraduate, Postgraduate, Not Graduated")
	private String graduationStatus;

	@NotBlank(message = "Employment status is required")
	@Pattern(regexp = "Employed|Unemployed|Self-Employed|Student", message = "Employment status must be one of: Employed, Unemployed, Self-Employed, Student")
	private String employementStatus;

	@NotBlank(message = "University name is required")
	private String universityName;

	@NotNull(message = "Passing year is required")
	@Min(value = 1950, message = "Invalid passing year")
	@Max(value = 2100, message = "Invalid passing year")
	private Integer passingYear;
}
