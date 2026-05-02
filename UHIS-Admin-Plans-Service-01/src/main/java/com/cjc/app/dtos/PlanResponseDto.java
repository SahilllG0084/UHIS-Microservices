package com.cjc.app.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PlanResponseDto {
	
	private Integer planId;

	private String planName;

	private String category;

	private LocalDate startDate;

	private LocalDate endDate;
}
