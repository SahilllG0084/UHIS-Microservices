package com.cjc.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cjc.app.entity.RegisterCitizen;

@Repository
public interface CitizenRepository extends JpaRepository<RegisterCitizen, Integer>{

	boolean existsByCaseNumber(String caseNumber);

	RegisterCitizen findByCaseNumber(String caseNumber);
}
