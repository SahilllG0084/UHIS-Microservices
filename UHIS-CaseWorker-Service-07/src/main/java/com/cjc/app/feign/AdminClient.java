package com.cjc.app.feign;

import java.util.List;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.cjc.app.dtos.CWRequestDto;
import com.cjc.app.dtos.CWResponseDto;
import com.cjc.app.dtos.PlanResponseDto;

@FeignClient(name = "UHIS-ADMIN-SERVICE", url = "http://localhost:7991")
public interface AdminClient {
    
	@GetMapping("/admin/module/plans")
	public ResponseEntity<List<PlanResponseDto>> getPlans();
	
	@PostMapping("/admin/module/admins")
	public ResponseEntity<CWResponseDto> addAdmin(@Valid @RequestBody CWRequestDto cwreqdto);
}
