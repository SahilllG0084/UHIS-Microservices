package com.cjc.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cjc.app.entity.Applicant;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer>{

	Applicant findByCaseNumber(String caseNumber);
}
