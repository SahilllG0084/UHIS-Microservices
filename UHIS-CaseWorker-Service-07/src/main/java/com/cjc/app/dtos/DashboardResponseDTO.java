package com.cjc.app.dtos;

import lombok.Data;

@Data
public class DashboardResponseDTO {
     
	private Integer noOfPlansAvailable;
	
	private Integer applicantReceived;
	
	private Long citizensApproved;
	
	private Long citizensDenied;
}
