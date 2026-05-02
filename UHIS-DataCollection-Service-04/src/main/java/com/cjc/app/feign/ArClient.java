package com.cjc.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cjc.app.dtos.CitizenResponseDTO;

@FeignClient(name = "UHIS-APPLICATION-REGISTRATION-SERVICE", url = "http://localhost:7992")
public interface ArClient {
   
	@GetMapping("/ar/module/citizens/{caseNumber}")
	public ResponseEntity<CitizenResponseDTO> getCitizenByCaseNo(@PathVariable String caseNumber);
}
