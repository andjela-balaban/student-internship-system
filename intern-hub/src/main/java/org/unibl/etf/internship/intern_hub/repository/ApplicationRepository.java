package org.unibl.etf.internship.intern_hub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import java.util.*;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	
	List<Application> findAllByInternshipInternshipId(Integer internshipId);
	
	Optional<Application> findByApplicationIdAndInternshipInternshipId(Integer applicationId, Integer internshipId);
	
	List<Application> findAllByStudentUserId(Integer userId);
	
	boolean existsByStudentUserIdAndInternshipInternshipId(Integer userId, Integer internshipId);
	
}
