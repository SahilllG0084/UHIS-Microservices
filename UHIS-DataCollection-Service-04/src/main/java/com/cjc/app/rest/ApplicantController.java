package com.cjc.app.rest;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cjc.app.dtos.AppSummaryResponseDTO;
import com.cjc.app.dtos.EducationRequestDTO;
import com.cjc.app.dtos.IncomeRequestDTO;
import com.cjc.app.dtos.KidsRequestDTO;
import com.cjc.app.dtos.PlanSelectionRequestDTO;
import com.cjc.app.service.ApplicantService;
import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@Slf4j
@RequestMapping("/dc/module")
public class ApplicantController {

	private ApplicantService appserv;

	public ApplicantController(ApplicantService appserv) {
		super();
		this.appserv = appserv;
	}

	@PostMapping("/plans")
	public ResponseEntity<String> savePlan(@Valid @RequestBody PlanSelectionRequestDTO pdto) {

		log.info("Received request to save plan for caseNumber: {}", pdto.getCaseNumber());

		appserv.savePlan(pdto);

		log.info("Plan saved successfully for caseNumber: {}", pdto.getCaseNumber());

		return ResponseEntity.ok("Plan Saved Successfully");
	}

	@PostMapping("/income/{caseNumber}")
	public ResponseEntity<String> saveIncome(@PathVariable String caseNumber,
			@RequestBody IncomeRequestDTO idto) {

		log.info("Received request to save income for caseNumber: {}", caseNumber);

		appserv.saveIncome(caseNumber, idto);

		log.info("Income saved successfully for caseNumber: {}", caseNumber);

		return ResponseEntity.ok("Income Saved Successfully");
	}

	@PostMapping("/education/{caseNumber}")
	public ResponseEntity<String> saveEducation(@PathVariable String caseNumber,
			@Valid	@RequestBody EducationRequestDTO edto) {

		log.info("Received request to save education for caseNumber: {}", caseNumber);

		appserv.saveEducation(caseNumber, edto);

		log.info("Education saved successfully for caseNumber: {}", caseNumber);

		return ResponseEntity.ok("Education Saved Successfully");
	}

	@PostMapping("/kids/{caseNumber}")
	public ResponseEntity<String> saveKids(@PathVariable String caseNumber,
			@RequestBody List<KidsRequestDTO> kdto) {

		log.info("Received request to save kids details for caseNumber: {}", caseNumber);

		appserv.saveKidsDetails(caseNumber, kdto);

		log.info("Kids details saved successfully for caseNumber: {}", caseNumber);

		return ResponseEntity.ok("Kids Details Saved Successfully");
	}

	@GetMapping("/summary/{caseNumber}")
	public ResponseEntity<AppSummaryResponseDTO> getSummary(@PathVariable String caseNumber) {

		log.info("Received request to fetch summary for caseNumber: {}", caseNumber);

		AppSummaryResponseDTO response = appserv.getSummary(caseNumber);

		log.info("Summary response sent for caseNumber: {}", caseNumber);

		return ResponseEntity.ok(response);
	}
}
