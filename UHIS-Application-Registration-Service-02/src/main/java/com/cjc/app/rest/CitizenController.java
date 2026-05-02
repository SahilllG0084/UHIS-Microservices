package com.cjc.app.rest;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.cjc.app.dto.CitizenRequestDTO;
import com.cjc.app.dto.CitizenResponseDTO;
import com.cjc.app.service.CitizenService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestControllerAdvice
@RequestMapping("/ar/module")
public class CitizenController {
    
	private CitizenService czserv;
	
	public CitizenController(CitizenService czserv) {
		super();
		this.czserv = czserv;
	}

	@PostMapping("/citizens")
    public ResponseEntity<CitizenResponseDTO> addCitizen(@Valid @RequestBody CitizenRequestDTO dto)
    {
		log.info("Received request to create citizen");
		log.debug("Request payload: {}", dto);
		log.info("Citizen created successfully");
        return new ResponseEntity<>(czserv.saveCitizen(dto), HttpStatus.CREATED);
    }
	
	@GetMapping("/citizens/{caseNumber}")
	public ResponseEntity<CitizenResponseDTO> getCitizenByCaseNo(@PathVariable String caseNumber)
	{
		log.info("Received request to fetch citizen by casenumber : ", caseNumber);
		return new ResponseEntity<CitizenResponseDTO>(czserv.getCitizen(caseNumber), HttpStatus.OK);
	}

    @GetMapping("/citizens")
    public ResponseEntity<List<CitizenResponseDTO>> getAllData() 
    {    
    	log.info("Received request to fetch all citizens");
        return new ResponseEntity<>(czserv.getAllCitizens(), HttpStatus.OK);
    }

	@PutMapping("/citizens/{id}")
	public ResponseEntity<CitizenResponseDTO> updateData(@PathVariable @NotNull @Min(1) Integer id, @RequestBody CitizenRequestDTO requestDTO)
	{
	    log.info("Received request to update citizen with ID: {}", id);
	    log.debug("Update payload: {}", requestDTO);
	    log.info("Citizen updated successfully for ID: {}", id);
		return new ResponseEntity<CitizenResponseDTO>(czserv.updateCitizenData(id, requestDTO), HttpStatus.OK);
	}
	
	@PatchMapping("/citizens/{id}")
	public ResponseEntity<CitizenResponseDTO> editData(@PathVariable @NotNull @Min(1) Integer id, @RequestBody CitizenRequestDTO requestDTO)
	{
	    log.info("Received request for partial update of citizen with ID: {}", id);
	    log.debug("Patch payload: {}", requestDTO);
	    log.info("Citizen partially updated for ID: {}", id);
		return new ResponseEntity<CitizenResponseDTO>(czserv.editCitizenData(id, requestDTO), HttpStatus.ACCEPTED);
	}

    @DeleteMapping("/citizens/{id}")
    public ResponseEntity<String> deleteCitizen(@PathVariable @NotNull @Min(1) Integer id) 
    {
        log.info("Received request to delete citizen with ID: {}", id);
        log.info("Delete operation completed for ID: {}", id);
        return new ResponseEntity<>(czserv.deleteCitizenById(id), HttpStatus.OK);
    }
}
