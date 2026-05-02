package com.cjc.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cjc.app.entity.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {

}
