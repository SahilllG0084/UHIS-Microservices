package com.cjc.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cjc.app.entity.SSAData;

@Repository
public interface SSARepository extends JpaRepository<SSAData, Integer>{
      
	SSAData findBySsano(String ssano);
}
