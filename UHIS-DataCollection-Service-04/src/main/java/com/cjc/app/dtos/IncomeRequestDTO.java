package com.cjc.app.dtos;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class IncomeRequestDTO {

	@NotBlank(message = "Case number is required")
	private String caseNumber;

	@PositiveOrZero(message = "Monthly salary must be zero or positive")
	@DecimalMin(value = "0.0", inclusive = true, message = "Monthly salary cannot be negative")
	private Double monthnlySalary;

	@PositiveOrZero(message = "Rent income must be zero or positive")
	@DecimalMin(value = "0.0", inclusive = true, message = "Rent income cannot be negative")
	private Double rentIncome;

	@PositiveOrZero(message = "Property income must be zero or positive")
	@DecimalMin(value = "0.0", inclusive = true, message = "Property income cannot be negative")
	private Double propertyIncome;
}
