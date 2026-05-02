package com.cjc.app.entity;

import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import lombok.*;

@Data
@NoArgsConstructor
@DynamicUpdate
@AllArgsConstructor
@Entity
public class Plans {
     
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer planId;
	
	private String planName;
	
	private String category;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
}