package com.cjc.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cjc.app.entity.PlanSelection;

@Repository
public interface PlanselectionRepository extends JpaRepository<PlanSelection, Integer> {

}
