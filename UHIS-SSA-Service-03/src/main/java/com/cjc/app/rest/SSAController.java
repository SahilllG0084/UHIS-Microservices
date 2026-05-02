package com.cjc.app.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cjc.app.entity.SSAData;
import com.cjc.app.service.SSAService;
import lombok.extern.slf4j.Slf4j;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class SSAController {
    
	 private SSAService service;

	 public SSAController(SSAService service) {
		super();
		this.service = service;
	 }
	 
	 @PostMapping("/ssas")
	 public ResponseEntity<SSAData> addData(@RequestBody SSAData ssaData)
	 {  
	    log.info("Received request to add SSA data");
		log.debug("Request payload: {}", ssaData);
		log.info("SSA data saved successfully");
		return new ResponseEntity<SSAData>(service.addSSAData(ssaData), HttpStatus.CREATED); 
	 }
	 
	 @GetMapping("/ssas")
	 public ResponseEntity<List<SSAData>> getData()
	 {
		log.info("Received request to fetch all SSA data"); 
		return new ResponseEntity<List<SSAData>>(service.getSSAData(), HttpStatus.OK); 
	 }
	 
	 @GetMapping("/ssas/{ssa}")
	 public ResponseEntity<SSAData> getSingleSSA(@PathVariable String ssa)
	 {
		 log.info("Received request to fetch SSA details for SSN: {}", ssa);
		 log.info("SSA data found for SSN: {}", ssa); 
		 return new ResponseEntity<SSAData>(service.getSSA(ssa), HttpStatus.ACCEPTED);
	 }
}
