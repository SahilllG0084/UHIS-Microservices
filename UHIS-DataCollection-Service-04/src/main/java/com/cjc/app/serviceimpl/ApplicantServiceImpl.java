package com.cjc.app.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.cjc.app.dao.ApplicantRepository;
import com.cjc.app.dao.EducationRepository;
import com.cjc.app.dao.IncomeRepository;
import com.cjc.app.dao.KidsRepository;
import com.cjc.app.dao.PlanselectionRepository;
import com.cjc.app.dtos.AppSummaryResponseDTO;
import com.cjc.app.dtos.CitizenResponseDTO;
import com.cjc.app.dtos.EducationRequestDTO;
import com.cjc.app.dtos.IncomeRequestDTO;
import com.cjc.app.dtos.KidsRequestDTO;
import com.cjc.app.dtos.PlanResponseDto;
import com.cjc.app.dtos.PlanSelectionRequestDTO;
import com.cjc.app.entity.Applicant;
import com.cjc.app.entity.Education;
import com.cjc.app.entity.Income;
import com.cjc.app.entity.KidsDetails;
import com.cjc.app.entity.PlanSelection;
import com.cjc.app.exception.ApplicantNotFoundException;
import com.cjc.app.exception.DataCollectionException;
import com.cjc.app.feign.AdminClient;
import com.cjc.app.feign.ArClient;
import com.cjc.app.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ApplicantServiceImpl implements ApplicantService {

	    private final AdminClient adminClient;
	    private final ArClient arClient;

	    private final ApplicantRepository apprepo;
	    private final IncomeRepository irepo;
	    private final EducationRepository erepo;
	    private final KidsRepository krepo;
	    private final PlanselectionRepository planrepo;
	    private final ModelMapper mapper;

	    @Override
	    public void savePlan(PlanSelectionRequestDTO pdto) {

	        log.info("Starting savePlan for caseNumber: {}", pdto.getCaseNumber());

	        try {

	            PlanResponseDto planResponseDto = adminClient.getPlan(pdto.getPlanId()).getBody();
	            log.debug("Fetched plan details from AdminClient for planId: {}", pdto.getPlanId());

	            PlanSelection plan = mapper.map(planResponseDto, PlanSelection.class);

	            plan.setCaseNumber(pdto.getCaseNumber());
	            PlanSelection savedPlan = planrepo.save(plan);
	            log.info("Plan saved successfully for caseNumber: {}", pdto.getCaseNumber());

	            CitizenResponseDTO crdto =
	                    arClient.getCitizenByCaseNo(pdto.getCaseNumber()).getBody();

	            log.debug("Fetched citizen details from AR module for caseNumber: {}", pdto.getCaseNumber());

	            Applicant applicant = mapper.map(crdto, Applicant.class);

	            applicant.setPlanSelection(savedPlan);
	            apprepo.save(applicant);

	            log.info("Applicant saved successfully with plan for caseNumber: {}", pdto.getCaseNumber());

	        } catch (Exception e) {
	            log.error("Error while saving plan for caseNumber: {}", pdto.getCaseNumber(), e);
	            throw new DataCollectionException("Failed To Save Plan Details..!");
	        }
	    }

	    @Override
	    public void saveIncome(String caseNumber, IncomeRequestDTO idto) {

	        log.info("Starting saveIncome for caseNumber: {}", caseNumber);

	       try {
	            Income income = mapper.map(idto, Income.class);
	            income.setCaseNumber(caseNumber);
	            Income savedIncome = irepo.save(income);

	            log.info("Income saved successfully for caseNumber: {}", caseNumber);

	            Applicant applicant = apprepo.findByCaseNumber(caseNumber);
	            applicant.setIncome(savedIncome);
	            apprepo.save(applicant);

	            log.debug("Applicant updated with income for caseNumber: {}", caseNumber);

	         } catch (Exception e) {
	            log.error("Error while saving income for caseNumber: {}", caseNumber, e);
	            throw new DataCollectionException("Failed To Save Income Details..!");
	        }
	    }

	    @Override
	    public void saveEducation(String caseNumber, EducationRequestDTO edto) {

	        log.info("Starting saveEducation for caseNumber: {}", caseNumber);

	        try {

	            Education education = mapper.map(edto, Education.class);
	            education.setCaseNumber(caseNumber);
	            Education savedEducation = erepo.save(education);

	            log.info("Education saved successfully for caseNumber: {}", caseNumber);

	            Applicant applicant = apprepo.findByCaseNumber(caseNumber);
	            applicant.setEducation(savedEducation);
	            apprepo.save(applicant);

	            log.debug("Applicant updated with education for caseNumber: {}", caseNumber);

	        } catch (Exception e) {
	            log.error("Error while saving education for caseNumber: {}", caseNumber, e);
	            throw new DataCollectionException("Failed To Save Education Details..!");
	        }
	    }

	    @Override
	    public void saveKidsDetails(String caseNumber, List<KidsRequestDTO> kdto) {

	        log.info("Starting saveKidsDetails for caseNumber: {}", caseNumber);

	        try {
	            List<KidsDetails> kidslist = new ArrayList<>();

	            for (KidsRequestDTO kidsdto : kdto) {
	                KidsDetails kidsDetails = mapper.map(kidsdto, KidsDetails.class);
	                kidsDetails.setCaseNumber(caseNumber);
	                kidslist.add(kidsDetails);
	            }

	            List<KidsDetails> savedKids = krepo.saveAll(kidslist);

	            log.info("Kids details saved successfully for caseNumber: {}", caseNumber);

	            Applicant applicant = apprepo.findByCaseNumber(caseNumber);
	            applicant.setKidsdetails(savedKids);
	            apprepo.save(applicant);

	            log.debug("Applicant updated with kids details for caseNumber: {}", caseNumber);

	        } catch (Exception e) {
	            log.error("Error while saving kids details for caseNumber: {}", caseNumber, e);
	            throw new DataCollectionException("Failed To Save Kids Details..!");
	        }
	    }

	    @Override
	    public AppSummaryResponseDTO getSummary(String caseNumber) {

	        log.info("Fetching summary for caseNumber: {}", caseNumber);

	        Applicant applicant = apprepo.findByCaseNumber(caseNumber);

	        if (applicant != null) {
	            AppSummaryResponseDTO summarydto = new AppSummaryResponseDTO();
	            summarydto.setApplicant(applicant);

	            log.info("Summary fetched successfully for caseNumber: {}", caseNumber);

	            return summarydto;
	        } 
	        else 
	        {
	            log.warn("No applicant found for caseNumber: {}", caseNumber);
	            throw new ApplicantNotFoundException("No applicant found for caseNumber : "+caseNumber);
	        }
	    }
}
