package org.unibl.etf.internship.intern_hub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import java.util.Optional;

@Repository
public interface CvRepository extends JpaRepository<Cv, Integer> {
	
	boolean existsByStudentUserId(Integer userId);

    Optional<Cv> findByStudentUserId(Integer userId);
    
}
