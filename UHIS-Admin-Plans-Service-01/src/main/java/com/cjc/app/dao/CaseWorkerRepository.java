package com.cjc.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cjc.app.entity.CaseWorker;

@Repository
public interface CaseWorkerRepository extends JpaRepository<CaseWorker, Integer>{

}
