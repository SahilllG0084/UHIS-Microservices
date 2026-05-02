package com.cjc.app.rest;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cjc.app.dtos.CWRequestDto;
import com.cjc.app.dtos.CWResponseDto;
import com.cjc.app.dtos.PlanRequestDto;
import com.cjc.app.dtos.PlanResponseDto;
import com.cjc.app.service.AdminService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequestMapping("/admin/module")
public class AdminController {
   
	private AdminService adminserv;

	public AdminController(AdminService adminserv) {
		super();
		this.adminserv = adminserv;
	}
	
	// Admin/CaseWorkers API
	
	@PostMapping("/admins")
	public ResponseEntity<CWResponseDto> addAdmin(@Valid @RequestBody CWRequestDto cwreqdto)
	{ 
		log.info("API CALL: Add Admin");
		return new ResponseEntity<CWResponseDto>(adminserv.saveAdmin(cwreqdto), HttpStatus.CREATED);
	}
	
	@GetMapping("/admins")
	public ResponseEntity<List<CWResponseDto>> getAdmins()
	{
		log.info("API CALL: Get All Admins");
		return new ResponseEntity<List<CWResponseDto>>(adminserv.getAllAdmins(), HttpStatus.OK);
	}
	
	@PutMapping("/admins/{id}")
	public ResponseEntity<CWResponseDto> updateAdminById(@PathVariable @NotNull @Min(1) Integer id, @RequestBody CWRequestDto cwreqdto)
	{
		log.info("API CALL: Update Admin ID {}", id);
		return new ResponseEntity<CWResponseDto>(adminserv.updateAdmin(id, cwreqdto), HttpStatus.ACCEPTED);
	}
	
	@PatchMapping("/admins/{id}")
	public ResponseEntity<CWResponseDto> editAdminById(@PathVariable @NotNull @Min(1) Integer id, @RequestBody CWRequestDto cwreqdto)
	{
		log.info("API CALL: Partial Update Admin ID {}", id);
		return new ResponseEntity<CWResponseDto>(adminserv.editAdmin(id, cwreqdto), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/admins/{id}")
	public ResponseEntity<String> deleteAdminById(@PathVariable @NotNull @Min(1) Integer id)
	{
		log.info("API CALL: Delete Admin ID {}", id);
		return new ResponseEntity<String>(adminserv.deleteAdmin(id), HttpStatus.OK);
	}
	
	// Plans API
	
	@PostMapping("/plans")
	public ResponseEntity<PlanResponseDto> addPlans(@Valid @RequestBody PlanRequestDto prdto)
	{
		log.info("API CALL: Add Plan {}", prdto.getPlanName());
		return new ResponseEntity<PlanResponseDto>(adminserv.savePlans(prdto), HttpStatus.CREATED);
	}
	
	@GetMapping("/plans/{id}")
	public ResponseEntity<PlanResponseDto> getPlan(@PathVariable @NotNull @Min(1) Integer id)
	{
		log.info("API CALL: Get Single Plan By Id..");
		return new ResponseEntity<PlanResponseDto>(adminserv.getPlanById(id), HttpStatus.OK);
	}
	
	@GetMapping("/plans")
	public ResponseEntity<List<PlanResponseDto>> getPlans()
	{
		log.info("API CALL: Get All Plans");
		return new ResponseEntity<List<PlanResponseDto>>(adminserv.getAllPlans(), HttpStatus.OK);
	}
	
	@PutMapping("/plans/{id}")
	public ResponseEntity<PlanResponseDto> updatePlanById(@PathVariable @NotNull @Min(1) Integer id, @RequestBody PlanRequestDto prdto)
	{
		log.info("API CALL: Update Plan ID {}", id);
	    return new ResponseEntity<PlanResponseDto>(adminserv.updatePlan(id, prdto), HttpStatus.ACCEPTED);	
	}
	
	@PatchMapping("/plans/{id}")
	public ResponseEntity<PlanResponseDto> editPlanById(@PathVariable @NotNull @Min(1) Integer id, @RequestBody PlanRequestDto prdto)
	{
		log.info("API CALL: Partial Update Plan ID {}", id);
	    return new ResponseEntity<PlanResponseDto>(adminserv.editPlan(id, prdto), HttpStatus.ACCEPTED);	
	}
	
	@DeleteMapping("/plans/{id}")
	public ResponseEntity<String> deletePlanById(@PathVariable @NotNull @Min(1) Integer id)
	{
		log.info("API CALL: Delete Plan ID {}", id);
		return new ResponseEntity<String>(adminserv.deletePlan(id), HttpStatus.OK);
	}
}
