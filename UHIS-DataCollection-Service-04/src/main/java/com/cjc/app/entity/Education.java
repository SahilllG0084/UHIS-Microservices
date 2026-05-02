package com.cjc.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "education_details")
public class Education {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long educationId;
    
    private String caseNumber;
    
    private String highestDegree;
    
    private String graduationStatus;
    
    private String employementStatus;

    private String universityName;    

    private Integer passingYear;
}
