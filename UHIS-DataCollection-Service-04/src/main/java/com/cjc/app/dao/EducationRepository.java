package com.cjc.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cjc.app.entity.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {

}
