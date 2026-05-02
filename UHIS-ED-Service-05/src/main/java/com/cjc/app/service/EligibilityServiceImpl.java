package com.cjc.app.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.cjc.app.dao.EligibilityRepository;
import com.cjc.app.dto.AppSummaryResponseDTO;
import com.cjc.app.dto.EligibilityResponseDTO;
import com.cjc.app.entity.Applicant;
import com.cjc.app.entity.Education;
import com.cjc.app.entity.EligibilityEntity;
import com.cjc.app.entity.Income;
import com.cjc.app.entity.Kids;
import com.cjc.app.entity.PlanSelection;
import com.cjc.app.feign.DcFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EligibilityServiceImpl implements EligibilityService {

	   private final EligibilityRepository elrepo;
	   private final ModelMapper mapper;
	   private final DcFeignClient dcClient;

	    @Override
	    public EligibilityResponseDTO checkEligibility(String caseNumber) {

	        log.info("Checking eligibility for caseNumber: {}", caseNumber);

	        ResponseEntity<AppSummaryResponseDTO> summary = dcClient.getSummary(caseNumber);
	        
	        log.info("Received applicant summary from DC module for caseNumber: {}", caseNumber);

	        if (summary == null || summary.getBody() == null || summary.getBody().getApplicant() == null)
	        {
	            log.error("Applicant summary is null for caseNumber: {}", caseNumber);
	            
	            throw new RuntimeException("Applicant summary not found for caseNumber: " + caseNumber);
	        }

	        EligibilityEntity elEntity = new EligibilityEntity();
	        elEntity.setCaseNumber(caseNumber);

	        Applicant applicant = summary.getBody().getApplicant();
	        Education education = applicant.getEducation();
	        Income income = applicant.getIncome();
	        List<Kids> kidsdetails = applicant.getKidsdetails();
	        PlanSelection planSelection = applicant.getPlanSelection();

	        if (planSelection == null || planSelection.getPlanName() == null) {
	            log.error("Plan selection is missing for caseNumber: {}", caseNumber);
	            throw new RuntimeException("Plan selection not found for caseNumber: " + caseNumber);
	        }

	        log.info("Executing eligibility rules for plan: {}", planSelection.getPlanName());

	        switch (planSelection.getPlanName()) {
	            case "SNAP":
	                executeSnapPlan(income, elEntity);
	                break;

	            case "CCAP":
	                executeCcapPlan(income, kidsdetails, elEntity);
	                break;

	            case "Medicaid":
	                executeMedicaidPlan(income, elEntity);
	                break;

	            case "Medicare":
	                executeMedicarePlan(applicant, elEntity);
	                break;

	            case "QHP":
	                executeQhpPlan(elEntity);
	                break;

	            case "NJW":
	                executeNjwPlan(education, elEntity);
	                break;

	            default:
	                log.warn("Invalid plan received for caseNumber: {}, planName: {}", caseNumber, planSelection.getPlanName());
	                elEntity.setPlanStatus("DENIED");
	                elEntity.setDenialReason("Invalid Plan...!");
	        }

	        elEntity.setFullName(applicant.getFullName());
	        elEntity.setPlanName(planSelection.getPlanName());
	        elEntity.setSsnNo(applicant.getSsnNo());
	        elEntity.setStartDate(LocalDate.now());
	        elEntity.setEndDate(LocalDate.now());

	        EligibilityEntity savedElEntity = elrepo.save(elEntity);
	        log.info("Eligibility result saved successfully for caseNumber: {}, status: {}", 
	                caseNumber, savedElEntity.getPlanStatus());

	        EligibilityResponseDTO responseDTO = mapper.map(savedElEntity, EligibilityResponseDTO.class);
	        return responseDTO;
	    }

	    private void executeSnapPlan(Income income, EligibilityEntity entity) {
	        log.debug("Executing SNAP plan rules");

	        if (income != null && income.getMonthnlySalary() <= 300) {
	            entity.setPlanStatus("APPROVED");
	            entity.setBenefitAmt(200.00);
	            log.info("SNAP plan approved");
	        } else {
	            entity.setPlanStatus("DENIED");
	            entity.setDenialReason("Income Is Higher Than Plan Requirements");
	            log.info("SNAP plan denied");
	        }
	    }

	    private void executeCcapPlan(Income income, List<Kids> kids, EligibilityEntity entity) {
	        log.debug("Executing CCAP plan rules");

	        boolean hasMinorKid = false;

	        if (kids != null) {
	            for (Kids kid : kids) {
	                if (kid != null && kid.getAge() <= 16) {
	                    hasMinorKid = true;
	                    break;
	                }
	            }
	        }

	        if (income != null && income.getMonthnlySalary() <= 300 && hasMinorKid) {
	            entity.setPlanStatus("APPROVED");
	            entity.setBenefitAmt(300.00);
	            log.info("CCAP plan approved");
	        } else {
	            entity.setPlanStatus("DENIED");
	            entity.setDenialReason("Citizen Has Minor Kid And High Income Than Plan Requirements");
	            log.info("CCAP plan denied");
	        }
	    }

	    private void executeMedicaidPlan(Income income, EligibilityEntity entity) {
	        log.debug("Executing Medicaid plan rules");

	        if (income != null && income.getMonthnlySalary() <= 300 && income.getPropertyIncome() <= 200) {
	            entity.setPlanStatus("APPROVED");
	            entity.setBenefitAmt(250.00);
	            log.info("Medicaid plan approved");
	        } else {
	            entity.setPlanStatus("DENIED");
	            entity.setDenialReason("Income Exceeds Medicaid Limit");
	            log.info("Medicaid plan denied");
	        }
	    }

	    private void executeMedicarePlan(Applicant applicant, EligibilityEntity entity) {
	        log.debug("Executing Medicare plan rules");

	        if (applicant != null && applicant.getAge() >= 65) {
	            entity.setPlanStatus("APPROVED");
	            entity.setBenefitAmt(150.00);
	            log.info("Medicare plan approved");
	        } else {
	            entity.setPlanStatus("DENIED");
	            entity.setDenialReason("Citizens Age Must Be Above 65");
	            log.info("Medicare plan denied");
	        }
	    }

	    private void executeQhpPlan(EligibilityEntity entity) {
	        log.debug("Executing QHP plan rules");

	        entity.setPlanStatus("APPROVED");
	        entity.setBenefitAmt(400.00);

	        log.info("QHP plan approved");
	    }

	    private void executeNjwPlan(Education education, EligibilityEntity entity) {
	        log.debug("Executing NJW plan rules");

	        boolean graduated = education != null && "COMPLETED".equalsIgnoreCase(education.getGraduationStatus());
	        boolean unemployed = education != null && "UNEMPLOYED".equalsIgnoreCase(education.getEmployementStatus());

	        if (graduated && unemployed) {
	            entity.setPlanStatus("APPROVED");
	            entity.setBenefitAmt(200.00);
	            log.info("NJW plan approved");
	        } else {
	            entity.setPlanStatus("DENIED");
	            entity.setDenialReason("NJW Eligibility Criteria Not Matches");
	            log.info("NJW plan denied");
	        }
	    }

	    @Override
	    public List<EligibilityEntity> getAllEligibleApplicants() {
	        log.info("Fetching all eligible applicants");
	        List<EligibilityEntity> list = elrepo.findAll();
	        log.info("Total eligibility records fetched: {}", list.size());
	        return list;
	    }

	    @Override
	    public ResponseEntity<Map<String, Long>> getEligibilityData() {

	        log.info("Fetching eligibility summary data");

	        List<EligibilityEntity> list = elrepo.findAll();

	        long denied = 0;
	        long approved = 0;

	        for (EligibilityEntity e : list) {
	            if (e.getPlanStatus() != null) {
	                if (e.getPlanStatus().equalsIgnoreCase("DENIED")) {
	                    denied++;
	                } else if (e.getPlanStatus().equalsIgnoreCase("APPROVED")) {
	                    approved++;
	                }
	            }
	        }

	        Map<String, Long> data = new HashMap<>();
	        data.put("DENIED", denied);
	        data.put("APPROVED", approved);

	        log.info("Eligibility summary calculated successfully. APPROVED: {}, DENIED: {}", approved, denied);

	        return new ResponseEntity<>(data, HttpStatus.OK);
	    }
}
