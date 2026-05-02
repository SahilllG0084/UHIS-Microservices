package com.cjc.app.service;

import java.util.List;
import com.cjc.app.dtos.CWRequestDto;
import com.cjc.app.dtos.CWResponseDto;
import com.cjc.app.dtos.PlanRequestDto;
import com.cjc.app.dtos.PlanResponseDto;

public interface AdminService {

	CWResponseDto saveAdmin(CWRequestDto cwreqdto);

	List<CWResponseDto> getAllAdmins();

	CWResponseDto updateAdmin(Integer id, CWRequestDto cwreqdto);

	CWResponseDto editAdmin(Integer id, CWRequestDto cwreqdto);

	String deleteAdmin(Integer id);

	PlanResponseDto savePlans(PlanRequestDto prdto);

	List<PlanResponseDto> getAllPlans();

	PlanResponseDto updatePlan(Integer id, PlanRequestDto prdto);

	PlanResponseDto editPlan(Integer id, PlanRequestDto prdto);

	String deletePlan(Integer id);

	PlanResponseDto getPlanById(Integer id);
}
