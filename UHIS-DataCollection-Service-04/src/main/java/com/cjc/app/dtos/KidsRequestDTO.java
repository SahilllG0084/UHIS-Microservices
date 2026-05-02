package com.cjc.app.dtos;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.Data;

@Data
public class KidsRequestDTO {
   
    @NotBlank(message = "Name required")
    private String name;

    @NotNull(message = "Age required")
    private Integer age;

    @NotBlank(message = "Gender required")
    private String gender;

    @NotNull(message = "DOB required")
    @Past
    private LocalDate dob;
    
    @NotBlank(message = "Case number is required")
    private String caseNumber;
    
    @NotBlank(message = "SSN No Required")
    private String ssnNO;
}
