package com.cjc.app.service;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.cjc.app.dtos.CWResponseDto;
import com.cjc.app.dtos.DashboardResponseDTO;
import com.cjc.app.dtos.LoginDto;
import com.cjc.app.dtos.ManageCaseWorkerRequestDto;

public interface CwService {

	ResponseEntity<String> saveCaseWorker(@Valid ManageCaseWorkerRequestDto mcwDTO);

	ResponseEntity<String> Login(@Valid LoginDto lDto);

	ResponseEntity<CWResponseDto> getCaseWorker(int id);

	ResponseEntity<String> forgetPassword(String email);

	ResponseEntity<String> updateCaseWorker(@Valid ManageCaseWorkerRequestDto mcwDTO, int id);

	ResponseEntity<DashboardResponseDTO> getDashBoard();
}
