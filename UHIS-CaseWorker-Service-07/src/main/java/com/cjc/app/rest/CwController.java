package com.cjc.app.rest;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cjc.app.dtos.CWResponseDto;
import com.cjc.app.dtos.DashboardResponseDTO;
import com.cjc.app.dtos.LoginDto;
import com.cjc.app.dtos.ManageCaseWorkerRequestDto;
import com.cjc.app.service.CwService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/cw-service/module")
public class CwController {
    
	private CwService cwServ;

	public CwController(CwService cwServ) {
		super();
		this.cwServ = cwServ;
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<String> addWorker(@Valid @RequestBody ManageCaseWorkerRequestDto mcwDTO) {
	    log.info("POST /register - Request received for email: {}", mcwDTO.getEmailId());
	    
	    ResponseEntity<String> response = cwServ.saveCaseWorker(mcwDTO);
	    
	    log.info("POST /register - CaseWorker registered successfully for email: {}", mcwDTO.getEmailId());
	    return response;
	}

	@PostMapping(value = "/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginDto lDto) {
	    log.info("POST /login - Login request received for email: {}", lDto.getEmailId());
	    
	    ResponseEntity<String> response = cwServ.Login(lDto);
	    
	    log.info("POST /login - Login process completed for email: {}", lDto.getEmailId());
	    return response;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CWResponseDto> getCaseWorkerById(@PathVariable int id) {
	    log.info("GET /{} - Fetch CaseWorker request received", id);
	    
	    ResponseEntity<CWResponseDto> response = cwServ.getCaseWorker(id);
	    
	    log.info("GET /{} - Fetch CaseWorker completed", id);
	    return response;
	}

	@GetMapping(value = "/forget-password/{email}")
	public ResponseEntity<String> forgetPassword(@PathVariable String email) {
	    log.info("GET /forget-password - Request received for email: {}", email);
	    
	    ResponseEntity<String> response = cwServ.forgetPassword(email);
	    
	    log.info("GET /forget-password - Process completed for email: {}", email);
	    return response;
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<String> updateWorker(@Valid @RequestBody ManageCaseWorkerRequestDto mcwDTO,
	                                           @PathVariable int id) {
	    log.info("PUT /{} - Update request received for email: {}", id, mcwDTO.getEmailId());
	    
	    ResponseEntity<String> response = cwServ.updateCaseWorker(mcwDTO, id);
	    
	    log.info("PUT /{} - Update completed", id);
	    return response;
	}

	@GetMapping(value = "/dashboard")
	public ResponseEntity<DashboardResponseDTO> getDashboard() {
	    log.info("GET /dashboard - Dashboard request received");
	    
	    ResponseEntity<DashboardResponseDTO> response = cwServ.getDashBoard();
	    
	    log.info("GET /dashboard - Dashboard response sent");
	    return response;
	}
}
