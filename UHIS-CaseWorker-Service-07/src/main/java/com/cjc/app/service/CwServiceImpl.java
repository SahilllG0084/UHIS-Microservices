package com.cjc.app.service;

import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cjc.app.dao.CwRepository;
import com.cjc.app.dtos.CWResponseDto;
import com.cjc.app.dtos.DashboardResponseDTO;
import com.cjc.app.dtos.EmailDTO;
import com.cjc.app.dtos.LoginDto;
import com.cjc.app.dtos.ManageCaseWorkerRequestDto;
import com.cjc.app.entity.ManageCaseWorker;
import com.cjc.app.enums.EmailTemplate;
import com.cjc.app.feign.ARClient;
import com.cjc.app.feign.AdminClient;
import com.cjc.app.feign.EligibilityClient;
import com.cjc.app.feign.EmailClient;
import com.cjc.app.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CwServiceImpl implements CwService{
	
	private final CwRepository cwRepo;
	private final ModelMapper modelMapper;
	private final AdminClient adminClient;
	private final ARClient arClient;
	private final EligibilityClient EClient;
	private final EmailClient emailClient;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Override
	public ResponseEntity<String> saveCaseWorker(@Valid ManageCaseWorkerRequestDto mcwDTO) {
	   
	    log.info("CaseWorker registration started for email: {}", mcwDTO.getEmailId());

	    ManageCaseWorker caseWorker = modelMapper.map(mcwDTO, ManageCaseWorker.class);

	    String rawPassword = generatePassword();

	    caseWorker.setStatus("ACTIVE");
	    caseWorker.setPassword(passwordEncoder.encode(rawPassword));

	    ManageCaseWorker dbCaseWorker = cwRepo.save(caseWorker);

	    if (dbCaseWorker != null) {

	        EmailDTO emailDto = new EmailDTO();

	        emailDto.setEmailTemplate(EmailTemplate.CASEWORKER_REGISTRATION);
	        emailDto.setTo(dbCaseWorker.getEmailId());
	        emailDto.setSubject("Caseworker Registration Successful....!");

	        emailDto.setFullName(dbCaseWorker.getFullName());
	        emailDto.setEmailId(dbCaseWorker.getEmailId());
	        emailDto.setPhoneNo(dbCaseWorker.getPhoneNo());

	        emailDto.setPassword(rawPassword);

	        try {
	            emailClient.sendMail(emailDto);
	            log.info("Registration email sent to email: {}", dbCaseWorker.getEmailId());
	        } catch (Exception e) {
	            log.error("Failed to send registration email to: {}", dbCaseWorker.getEmailId(), e);
	        }
	        return new ResponseEntity<>("CaseWorker registered successfully", HttpStatus.CREATED);
	    }

	    log.error("CaseWorker registration failed for email: {}", mcwDTO.getEmailId());
	    return new ResponseEntity<>("Problem occurred", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<String> Login(@Valid LoginDto lDto) {
		
	    log.info("Login request received for email: {}", lDto.getEmailId());

	    ManageCaseWorker caseWorker = cwRepo.findByEmailId(lDto.getEmailId());

	    if (caseWorker == null) {
	        log.warn("Login failed: email not found {}", lDto.getEmailId());
	        throw new RuntimeException("Invalid Credentials");
	    }

	    boolean passwordMatched =
	            passwordEncoder.matches(lDto.getPassword(), caseWorker.getPassword());

	    if (!passwordMatched) {
	        log.warn("Login failed: invalid password for email {}", lDto.getEmailId());
	        throw new RuntimeException("Invalid Credentials");
	    }

	    String token = jwtUtil.generateToken(caseWorker.getEmailId());

	    log.info("Login successful and JWT generated for email: {}", lDto.getEmailId());

	    return ResponseEntity.ok(token);
	}

	@Override
	public ResponseEntity<CWResponseDto> getCaseWorker(int id) {		

	    log.info("Fetching CaseWorker for id: {}", id);

	    ManageCaseWorker workerId = cwRepo.findById(id).get();

	    if (workerId == null) {
	        throw new RuntimeException("CaseWorker not found");
	    }

	    CWResponseDto dto = modelMapper.map(workerId, CWResponseDto.class);

	    log.info("CaseWorker fetched successfully for id: {}", id);

	    return ResponseEntity.ok(dto);
	}

	@Override
	public ResponseEntity<String> forgetPassword(String email) {
		
		 log.info("Forgot password request received for email: {}", email);

		    ManageCaseWorker caseWorker = cwRepo.findByEmailId(email);

		    if (caseWorker == null) {
		        log.warn("Forgot password failed: email not found {}", email);
		        throw new RuntimeException("Invalid Email Id");
		    }

		    String rawPassword = generatePassword();

		    caseWorker.setPassword(passwordEncoder.encode(rawPassword));
		    cwRepo.save(caseWorker);

		    // sending email with raw password
		    EmailDTO emailDto = new EmailDTO();
		    emailDto.setEmailTemplate(EmailTemplate.FORGOT_PASSWORD);
		    emailDto.setTo(caseWorker.getEmailId());
		    emailDto.setSubject("Password Reset Successful");

		    emailDto.setFullName(caseWorker.getFullName());
		    emailDto.setEmailId(caseWorker.getEmailId());
		    emailDto.setPassword(rawPassword);

		    try {
		        emailClient.sendMail(emailDto);
		        log.info("Password reset email sent successfully to: {}", email);
		    } catch (Exception e) {
		        log.error("Failed to send password reset email to: {}", email, e);
		    }

		    return ResponseEntity.ok("Password reset successfully");
	}

	@Override
	public ResponseEntity<String> updateCaseWorker(@Valid ManageCaseWorkerRequestDto mcwDTO, int id) {
		
	    log.info("Update CaseWorker request received for id: {}", id);

	    ManageCaseWorker dbWorker = cwRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("CaseWorker not found"));

	    dbWorker.setFullName(mcwDTO.getFullName());
	    dbWorker.setPhoneNo(mcwDTO.getPhoneNo());
	    dbWorker.setEmailId(mcwDTO.getEmailId());
	    dbWorker.setBranchName(mcwDTO.getBranchName());
	    dbWorker.setStatus(mcwDTO.getStatus());

	    cwRepo.save(dbWorker);

	    log.info("CaseWorker updated successfully for id: {}", id);

	    return ResponseEntity.ok("CaseWorker updated successfully");
	}

	@Override
	public ResponseEntity<DashboardResponseDTO> getDashBoard() {
		
		log.info("Dashboard request received");

		int totalPlans = adminClient.getPlans().getBody().size();
		int totalCitizen = arClient.getAllData().getBody().size();

		ResponseEntity<Map<String, Long>> eligibilityData = EClient.getEntireEdData();
		Map<String, Long> data = eligibilityData.getBody();

		Long deniedCitizen = data.getOrDefault("DENIED", 0L);
		Long approvedCitizen = data.getOrDefault("APPROVED", 0L);

		DashboardResponseDTO dto = new DashboardResponseDTO();

		dto.setNoOfPlansAvailable(totalPlans);
		dto.setApplicantReceived(totalCitizen);
		dto.setCitizensDenied(deniedCitizen);
		dto.setCitizensApproved(approvedCitizen);

		log.info("Dashboard response prepared successfully");

		return ResponseEntity.ok(dto);
	}
	
	// generating random password
	private String generatePassword() {
	    String uuid = UUID.randomUUID().toString().replace("-", "");
	    String password = uuid.substring(0, 8) + "@" + uuid.substring(8, 12);
	    log.debug("Temporary password generated");
	    return password;
	}
}
