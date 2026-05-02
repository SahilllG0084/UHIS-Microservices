package com.cjc.app.feign;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "UHIS-ED-SERVICE" , url = "http://localhost:7995")
public interface EligibilityClient {
    
    @GetMapping("/ed/module/data")
    public ResponseEntity<Map<String, Long>> getEntireEdData();
}
