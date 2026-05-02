package com.cjc.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.cjc.app.dtos.EmailDTO;

@FeignClient(name = "UHIS-CORRESPONDENCE-SERVICE", url = "http://localhost:7996")
public interface EmailClient {
    
	@PostMapping("/crd/module/mails")
	public ResponseEntity<String> sendMail(@RequestBody EmailDTO edto);
}
