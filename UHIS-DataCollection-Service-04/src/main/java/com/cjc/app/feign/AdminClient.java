package com.cjc.app.feign;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cjc.app.dtos.PlanResponseDto;

@FeignClient(name = "UHIS-ADMIN-SERVICE", url = "http://localhost:7991")
public interface AdminClient {
    
	@GetMapping("/admin/module/plans/{id}")
	public ResponseEntity<PlanResponseDto> getPlan(@PathVariable @NotNull @Min(1) Integer id);
}
