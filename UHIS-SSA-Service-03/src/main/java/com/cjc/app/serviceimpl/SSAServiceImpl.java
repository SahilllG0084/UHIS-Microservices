package com.cjc.app.serviceimpl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cjc.app.dao.SSARepository;
import com.cjc.app.entity.SSAData;
import com.cjc.app.exception.SSANotFoundException;
import com.cjc.app.service.SSAService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SSAServiceImpl implements SSAService {
   
	private SSARepository repo;

	public SSAServiceImpl(SSARepository repo) {
		super();
		this.repo = repo;
	}
	
	@Override
	public SSAData addSSAData(SSAData ssaData) {
		
		log.info("Saving SSA data for SSN: {}", ssaData.getSsano());
		return repo.save(ssaData);	
	}
	
	@Override
	public List<SSAData> getSSAData() {
		
		log.info("Fetching all SSA records");
		return repo.findAll();
	}
	
	@Override
	public SSAData getSSA(String ssa) {		
		
		log.info("Fetching SSA data for SSN: {}", ssa);
		SSAData ssano = repo.findBySsano(ssa);
		
		if(ssano != null)
		{
			log.info("SSA record found for SSN: {}", ssa);
			return ssano;
		}
		
		log.error("SSA record not found for SSN: {}", ssa);
		throw new SSANotFoundException("SSN Not Exist..! Enter Valid SSN..!!");
	}
}
