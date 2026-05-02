package com.cjc.app.dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenResponseDTO {
   
	private String caseNumber;
    private String fullName;
    private String email;
    private Long mobileNo;
    private Integer age;
    private String gender;
    private LocalDate dob;
    private Long ssnNo;
    private String stateName;
    private String country;
}
