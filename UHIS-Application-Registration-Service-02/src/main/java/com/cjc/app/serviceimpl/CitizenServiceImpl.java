package com.cjc.app.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.cjc.app.dao.CitizenRepository;
import com.cjc.app.dto.CitizenRequestDTO;
import com.cjc.app.dto.CitizenResponseDTO;
import com.cjc.app.dto.StateDTO;
import com.cjc.app.entity.RegisterCitizen;
import com.cjc.app.enums.State;
import com.cjc.app.exception.DataNotAvailableException;
import com.cjc.app.exception.StateNotValidException;
import com.cjc.app.service.CitizenService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CitizenServiceImpl implements CitizenService {

	private static final String SSA_URL = "http://localhost:7993/api/v1/ssas/{ssa}";

	private final CitizenRepository czrepo;
	private final RestTemplate restTemplate;
	private final ModelMapper modelMapper;

	public CitizenServiceImpl(CitizenRepository czrepo, RestTemplate restTemplate, ModelMapper modelMapper) {
		this.czrepo = czrepo;
		this.restTemplate = restTemplate;
		this.modelMapper = modelMapper;
	}

	@Override
	public CitizenResponseDTO saveCitizen(CitizenRequestDTO dto) {

		log.info("Starting citizen registration for SSN: {}", dto.getSsnNo());

		// Call SSA service
		StateDTO stateDto = restTemplate.getForObject(SSA_URL, StateDTO.class, dto.getSsnNo());

		if (stateDto == null) 
		{
			log.error("No response from SSA service for SSN: {}", dto.getSsnNo());
			throw new StateNotValidException("No response from SSA service");
		}

		log.info("Received state: {}, country: {}", stateDto.getState(), stateDto.getCountry());

		State stateEnum;
		try 
		{		
			stateEnum = State.valueOf(stateDto.getState().toUpperCase());
		}
		catch (IllegalArgumentException e)
		{
			log.error("Invalid state received from SSA: {}", stateDto.getState());
			throw new StateNotValidException("Invalid state received from SSA service");
		}

		if (stateEnum != State.MAHARASHTRA)
		{
			log.error("Citizen does not belong to Maharashtra. Registration rejected for SSN: {}", dto.getSsnNo());
			throw new StateNotValidException("Citizen should belong to Maharashtra");
		}

		RegisterCitizen citizen = modelMapper.map(dto, RegisterCitizen.class);

		citizen.setCaseNumber(generateUniqueCaseNumber());

		// Set values received from SSA
		citizen.setStateName(stateEnum);
		citizen.setCountry(stateDto.getCountry());

		RegisterCitizen savedCitizen = czrepo.save(citizen);

		log.info("Citizen saved successfully with ID: {} and Case Number: {}", savedCitizen.getCid(),savedCitizen.getCaseNumber());

		return mapToResponse(savedCitizen);
	}
	
	@Override
	public CitizenResponseDTO getCitizen(String caseNumber) {
		
		log.info("Fetching single citizen from database");
		RegisterCitizen dbcitizen = czrepo.findByCaseNumber(caseNumber);
		
		CitizenResponseDTO citizen = modelMapper.map(dbcitizen, CitizenResponseDTO.class);
		
		log.info("Citizen Fetched Successfully For Given Case Number :", caseNumber);
		return citizen;
	}

	@Override
	public List<CitizenResponseDTO> getAllCitizens() {

		log.info("Fetching all citizens from database");

		List<RegisterCitizen> citizens = czrepo.findAll();

		log.debug("Total records found: {}", citizens.size());

		List<CitizenResponseDTO> responseList = new ArrayList<>();

		for (RegisterCitizen citizen : citizens) {
			responseList.add(mapToResponse(citizen));
		}

		return responseList;
	}

	@Override
	public CitizenResponseDTO updateCitizenData(Integer id, CitizenRequestDTO requestDTO) {

		log.info("Updating citizen with ID: {}", id);

		RegisterCitizen citizen = czrepo.findById(id).orElseThrow(() -> {
			log.error("Citizen not found for ID: {}", id);
			return new DataNotAvailableException("No data available for given citizen ID");
		});

		citizen.setFullName(requestDTO.getFullName());
		citizen.setGender(requestDTO.getGender());
		citizen.setEmail(requestDTO.getEmail());
		citizen.setMobileNo(requestDTO.getMobileNo());
		citizen.setDob(requestDTO.getDob());
		citizen.setAge(requestDTO.getAge());

		RegisterCitizen updatedCitizen = czrepo.save(citizen);

		log.info("Citizen updated successfully for ID: {}", id);

		return mapToResponse(updatedCitizen);
	}

	@Override
	public CitizenResponseDTO editCitizenData(Integer id, CitizenRequestDTO requestDTO) {

		log.info("Partially updating citizen with ID: {}", id);

		RegisterCitizen citizen = czrepo.findById(id).orElseThrow(() -> {
			log.error("Citizen not found for partial update, ID: {}", id);
			return new DataNotAvailableException("No data available for given citizen ID");
		});

		if (requestDTO.getFullName() != null) {
			citizen.setFullName(requestDTO.getFullName());
		}
		if (requestDTO.getEmail() != null) {
			citizen.setEmail(requestDTO.getEmail());
		}
		if (requestDTO.getGender() != null) {
			citizen.setGender(requestDTO.getGender());
		}
		if (requestDTO.getDob() != null) {
			citizen.setDob(requestDTO.getDob());
		}
		if (requestDTO.getMobileNo() != null) {
			citizen.setMobileNo(requestDTO.getMobileNo());
		}
		if (requestDTO.getAge() != null) {
			citizen.setAge(requestDTO.getAge());
		}

		RegisterCitizen updatedCitizen = czrepo.save(citizen);

		log.info("Citizen partially updated successfully for ID: {}", id);

		return mapToResponse(updatedCitizen);
	}

	@Override
	public String deleteCitizenById(Integer id) {

		log.info("Deleting citizen with ID: {}", id);
         
		if(czrepo.existsById(id))
		{
			czrepo.deleteById(id);
			
			log.info("Citizen deleted successfully for ID: {}", id);
			
			return "Citizen deleted successfully for given ID: " + id;
		}
		  log.warn("No Citizen Found For Given Id : " +id);
		  return "No Citizen Found For Given Id : "+id;
	}

	private String generateUniqueCaseNumber() {
		String caseNumber;
		do {
			caseNumber = "CASE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		} while (czrepo.existsByCaseNumber(caseNumber));
		return caseNumber;
	}

	private CitizenResponseDTO mapToResponse(RegisterCitizen citizen) {
		CitizenResponseDTO response = modelMapper.map(citizen, CitizenResponseDTO.class);

		if (citizen.getStateName() != null) {
			response.setStateName(citizen.getStateName().name());
		}

		return response;
	}
}
