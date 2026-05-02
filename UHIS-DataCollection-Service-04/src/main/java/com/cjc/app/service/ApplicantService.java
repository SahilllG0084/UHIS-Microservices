package com.cjc.app.service;

import java.util.List;
import com.cjc.app.dtos.AppSummaryResponseDTO;
import com.cjc.app.dtos.EducationRequestDTO;
import com.cjc.app.dtos.IncomeRequestDTO;
import com.cjc.app.dtos.KidsRequestDTO;
import com.cjc.app.dtos.PlanSelectionRequestDTO;

public interface ApplicantService {

	void savePlan(PlanSelectionRequestDTO pdto);

	void saveIncome(String caseNumber, IncomeRequestDTO idto);

	void saveEducation(String caseNumber, EducationRequestDTO edto);

	void saveKidsDetails(String caseNumber, List<KidsRequestDTO> kdto);

	AppSummaryResponseDTO getSummary(String caseNumber);
}
