package com.cjc.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cjc.app.dto.AppSummaryResponseDTO;

@FeignClient(name = "UHIS-DATACOLLECTION-SERVICE", url = "http://localhost:7994")
public interface DcFeignClient {
    
	@GetMapping("/dc/module/summary/{caseNumber}")
	public ResponseEntity<AppSummaryResponseDTO> getSummary(@PathVariable String caseNumber);
}
