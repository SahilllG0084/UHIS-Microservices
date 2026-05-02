package com.cjc.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cjc.app.entity.EligibilityEntity;

@Repository
public interface EligibilityRepository extends JpaRepository<EligibilityEntity, Integer> {

}
