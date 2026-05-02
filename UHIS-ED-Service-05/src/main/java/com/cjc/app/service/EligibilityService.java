package com.cjc.app.service;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.cjc.app.dto.EligibilityResponseDTO;
import com.cjc.app.entity.EligibilityEntity;

public interface EligibilityService {

	EligibilityResponseDTO checkEligibility(String caseNumber);

	List<EligibilityEntity> getAllEligibleApplicants();

	ResponseEntity<Map<String, Long>> getEligibilityData();
}
