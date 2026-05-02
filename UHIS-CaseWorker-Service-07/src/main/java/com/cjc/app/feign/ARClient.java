package com.cjc.app.feign;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.cjc.app.dtos.CitizenResponseDTO;

@FeignClient(name = "UHIS-APPLICATION-REGISTRATION-SERVICE", url = "http://localhost:7992")
public interface ARClient {
    
    @GetMapping("/ar/module/citizens")
    public ResponseEntity<List<CitizenResponseDTO>> getAllData();
}
