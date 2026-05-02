package com.cjc.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cjc.app.entity.ManageCaseWorker;

@Repository
public interface CwRepository extends JpaRepository<ManageCaseWorker, Integer>{

	ManageCaseWorker findByEmailId(String emailId);

	//ManageCaseWorker findByCaseWorkerId(int id);
}
