package com.cjc.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cjc.app.entity.KidsDetails;

@Repository
public interface KidsRepository extends JpaRepository<KidsDetails, Integer> {

}
