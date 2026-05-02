package com.cjc.app.serviceimpl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.cjc.app.dao.CaseWorkerRepository;
import com.cjc.app.dao.PlanRepository;
import com.cjc.app.dtos.CWRequestDto;
import com.cjc.app.dtos.CWResponseDto;
import com.cjc.app.dtos.PlanRequestDto;
import com.cjc.app.dtos.PlanResponseDto;
import com.cjc.app.entity.CaseWorker;
import com.cjc.app.entity.Plans;
import com.cjc.app.enums.Status;
import com.cjc.app.exception.CaseWorkerNotExist;
import com.cjc.app.exception.DataNotAvailable;
import com.cjc.app.exception.PlanNotExist;
import com.cjc.app.service.AdminService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
	
	private CaseWorkerRepository cwrepo;	
	private PlanRepository planrepo; 
	private ModelMapper modelMapper;
	private JavaMailSender mailSender;
	
	public AdminServiceImpl(CaseWorkerRepository cwrepo, PlanRepository planrepo, ModelMapper modelMapper, JavaMailSender mailSender) 
	{
		super();
		this.cwrepo = cwrepo;
		this.planrepo = planrepo;
		this.modelMapper = modelMapper;
		this.mailSender = mailSender;
	}

	@Override
	public CWResponseDto saveAdmin(CWRequestDto cwreqdto) {
		
	  log.info("Saving new Admin: {}", cwreqdto.getEmailId());
	  CaseWorker caseWorker = modelMapper.map(cwreqdto, CaseWorker.class);
	  caseWorker.setPassword(generatePassword());
	  caseWorker.setStatus(Status.Inactive);
	  
	  CaseWorker savedCaseWorker = cwrepo.save(caseWorker);
	  log.info("Admin saved successfully with ID: {}", savedCaseWorker.getAccountId());
	  
	  SimpleMailMessage mail = new SimpleMailMessage();
      mail.getSentDate();
      mail.setSubject("Registration Successful....!");
      mail.setText("Mr/Mrs " + savedCaseWorker.getFullName() + " Your Registration Has Successful..!" +
      " This Is Your AccountId : "+savedCaseWorker.getAccountId() +
      " And Password Is : '"+savedCaseWorker.getPassword()+"'. Account Status : "+savedCaseWorker.getStatus()+ ", After Verify Your Account Status Will Be Change...!");
      mail.setTo(savedCaseWorker.getEmailId());
      mailSender.send(mail);
      
      log.info("Email sent successfully to {}", savedCaseWorker.getEmailId());
		
	  CWResponseDto responseDto = modelMapper.map(savedCaseWorker, CWResponseDto.class); 
	  
	  return responseDto;
	}
	
	@Override
	public List<CWResponseDto> getAllAdmins() {
		
		log.info("Fetching all admins");
		List<CaseWorker> cwlist = cwrepo.findAll();
		
		if(cwlist != null)
		{
			List<CWResponseDto> getallcws = new ArrayList<>();
			
			for(CaseWorker CWs : cwlist)
			{
				getallcws.add(modelMapper.map(CWs, CWResponseDto.class));
			}
			return getallcws;
		}
		 
        log.warn("No admins found in database");
	    throw new DataNotAvailable("Empty List : No Data Present In The DB..!");
	}
	
	@Override
	public CWResponseDto updateAdmin(Integer id, CWRequestDto cwreqdto) {
		
		log.info("Updating Admin with ID: {}", id);
		
        if(cwrepo.existsById(id))
        {
        	CaseWorker caseWorker = cwrepo.findById(id).get();
        	
        	caseWorker.setAccountId(cwreqdto.getAccountId());
        	caseWorker.setFullName(cwreqdto.getFullName());
        	caseWorker.setGender(cwreqdto.getGender());
        	caseWorker.setBirthDate(cwreqdto.getBirthDate());
        	caseWorker.setEmailId(cwreqdto.getEmailId());
        	caseWorker.setPhoneNo(cwreqdto.getPhoneNo());
        	caseWorker.setSsnNo(cwreqdto.getSsnNo());
        	caseWorker.setCreateDate(cwreqdto.getCreateDate());
        	caseWorker.setUpdateDate(cwreqdto.getUpdateDate());
        	
        	CWResponseDto responseDto = modelMapper.map(cwrepo.save(caseWorker), CWResponseDto.class);
        	log.info("Admin updated successfully: {}", id);
        	
        	return responseDto;
        }
		
        log.error("Admin not found with ID: {}", id);
		throw new CaseWorkerNotExist("CaseWorker/Admin Not Exist For Given Id :"+id+ ",Plz Enter Valid Id...");
	}
	
	@Override
	public CWResponseDto editAdmin(Integer id, CWRequestDto cwreqdto) {
		
		log.info("Partially updating Admin with ID: {}", id);
		
		if(cwrepo.existsById(id))
		{
			CaseWorker caseWorker = cwrepo.findById(id).get();
			
			if(cwreqdto.getAccountId() != null) caseWorker.setAccountId(cwreqdto.getAccountId());
			if(cwreqdto.getFullName() != null) caseWorker.setFullName(cwreqdto.getFullName());
			if(cwreqdto.getBirthDate() != null) caseWorker.setBirthDate(cwreqdto.getBirthDate());
			if(cwreqdto.getGender() != null) caseWorker.setGender(cwreqdto.getGender());
			if(cwreqdto.getPhoneNo() != null) caseWorker.setPhoneNo(cwreqdto.getPhoneNo());
			if(cwreqdto.getEmailId() != null) caseWorker.setEmailId(cwreqdto.getEmailId());
			if(cwreqdto.getSsnNo() != null) caseWorker.setSsnNo(cwreqdto.getSsnNo());
			if(cwreqdto.getCreateDate() != null) caseWorker.setCreateDate(cwreqdto.getCreateDate());
			if(cwreqdto.getUpdateDate() != null) caseWorker.setUpdateDate(cwreqdto.getUpdateDate());
			
			CWResponseDto responseDto = modelMapper.map(cwrepo.save(caseWorker), CWResponseDto.class);
			log.info("Admin partially updated: {}", id);
			
			return responseDto;
		}
		
		log.error("Admin not found for patch update: {}", id);
		throw new CaseWorkerNotExist("CaseWorker/Admin Not Exist For Given Id :"+id+ ", Plz Enter Valid Id...");
	}
	
	@Override
	public String deleteAdmin(Integer id) {
		
		 log.info("Deleting Admin with ID: {}", id);
		
		if(cwrepo.existsById(id))
		{
			cwrepo.deleteById(id);
			
			log.info("Admin deleted successfully: {}", id);
			
			return "CaseWorker/Admin Deleted Successfully For Given Id : "+id;
		}
		 log.error("Delete failed, Admin not found: {}", id);	
         throw new CaseWorkerNotExist("CaseWorker/Admin Not Exist For Given Id :"+id+ ",Plz Enter Valid Id...");
	}
	
	//Plan : 
	
	@Override
	public PlanResponseDto savePlans(PlanRequestDto prdto) {
	
		log.info("Saving new plan: {}", prdto.getPlanName());
		
		modelMapper.map(prdto, Plans.class);		
		return modelMapper.map(planrepo.save(modelMapper.map(prdto, Plans.class)), PlanResponseDto.class);
	}
	
	@Override
	public List<PlanResponseDto> getAllPlans() {
		
		  log.info("Fetching all plans");
		
		  List<Plans> planslist = planrepo.findAll();
		
		  List<PlanResponseDto> plansDto = new ArrayList<PlanResponseDto>();
		
		  for(Plans plans : planslist)
		  {
			 plansDto.add(modelMapper.map(plans, PlanResponseDto.class));
		  }
		  
		  log.info("Plans fetched Successfully..");
		  return plansDto;
	}
	
	@Override
	public PlanResponseDto getPlanById(Integer id) {
		
		log.info("Finding plan By ID: {}", id);
		
		if(planrepo.existsById(id))
		{
			Plans plans = planrepo.findById(id).get();
			
			PlanResponseDto dto = modelMapper.map(plans, PlanResponseDto.class);
			
			log.debug("Plan Fetched Successfully...!");
			return dto;
		}
		   log.warn("Not Valid Id For Finding Plan..!");
          throw new PlanNotExist("Plan Not Exist For Given Id : "+id);
	}
	
	@Override
	public PlanResponseDto updatePlan(Integer id, PlanRequestDto prdto) {
        
		log.info("Updating plan with ID: {}", id);
		
		if(planrepo.existsById(id))
		{
			Plans plans = planrepo.findById(id).get();

			plans.setPlanName(prdto.getPlanName());
			plans.setCategory(prdto.getCategory());
			plans.setStartDate(prdto.getStartDate());
			plans.setEndDate(prdto.getEndDate());
			
			PlanResponseDto plandto = modelMapper.map(planrepo.save(plans), PlanResponseDto.class);
			log.info("Plan updated successfully: {}", id);
			return plandto;
		}
         
		log.error("Plan not found: {}", id);
		throw new PlanNotExist("Plan Not Exist For Given Id :" +id+ ", Enter Valid Id...!");
	}
	
	@Override
	public PlanResponseDto editPlan(Integer id, PlanRequestDto prdto) {
		
		log.info("Partially updating Plan with ID: {}", id);
		
		if(planrepo.existsById(id))
		{
			Plans plans = planrepo.findById(id).get();
			
			if(prdto.getPlanName() != null) plans.setPlanName(prdto.getPlanName());
			if(prdto.getCategory() != null) plans.setCategory(prdto.getCategory());
			if(prdto.getStartDate() != null) plans.setStartDate(prdto.getStartDate());
			if(prdto.getEndDate() != null) plans.setEndDate(prdto.getEndDate());
			
			PlanResponseDto responseDto = modelMapper.map(planrepo.save(plans), PlanResponseDto.class);
			log.info("Plan partially updated: {}", id);
			return responseDto;
		}
		
		log.error("Plan not found for patch update: {}", id);
		throw new PlanNotExist("Plan Not Exist For Given Id :" +id+ ", Enter Valid Id...!");
	}
	
	@Override
	public String deletePlan(Integer id) {
		
		log.info("Deleting plan with ID: {}", id);
		
		if(planrepo.existsById(id))
		{
			planrepo.deleteById(id);
			
			log.info("Plan deleted successfully: {}", id);
			return "Plan Deleted Successfully For Given Id :"+id;
		}
		
		log.error("Delete failed, plan not found: {}", id);
		throw new PlanNotExist("Plan Not Exist For Given Id :" +id+ ", Enter Valid Id...!");
	}
	
	//generating random password
	private String generatePassword()
	{
        final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*!";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            sb.append(CHAR_SET.charAt(random.nextInt(CHAR_SET.length())));
        }
        return sb.toString();
    }
}
