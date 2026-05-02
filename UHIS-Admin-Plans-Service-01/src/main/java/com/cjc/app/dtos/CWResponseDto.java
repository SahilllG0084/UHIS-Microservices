package com.cjc.app.dtos;

import java.time.LocalDate;
import com.cjc.app.enums.Status;
import lombok.Data;

@Data
public class CWResponseDto {
	
	private Integer adminId;

	private Long accountId;

	private String fullName;

	private String emailId;
	
	private Long phoneNo;

	private LocalDate birthDate;

	private String gender;

	private Long ssnNo;

	private LocalDate createDate;

	private LocalDate updateDate;

	private Status status;
}
