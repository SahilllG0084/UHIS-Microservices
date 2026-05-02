package com.cjc.app.service;

import java.util.List;
import com.cjc.app.dto.CitizenRequestDTO;
import com.cjc.app.dto.CitizenResponseDTO;

public interface CitizenService {

	CitizenResponseDTO saveCitizen(CitizenRequestDTO dto);

	List<CitizenResponseDTO> getAllCitizens();

	CitizenResponseDTO updateCitizenData(Integer id, CitizenRequestDTO requestDTO);

	CitizenResponseDTO editCitizenData(Integer id, CitizenRequestDTO requestDTO);

	String deleteCitizenById(Integer id);

	CitizenResponseDTO getCitizen(String caseNumber);
}
