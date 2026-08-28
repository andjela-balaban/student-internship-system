package org.unibl.etf.internship.intern_hub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import java.util.*;

@Repository
public interface InternshipLogRepository extends JpaRepository<InternshipLog, Integer>, JpaSpecificationExecutor<InternshipLog> {
	
	List<InternshipLog> findAllByStudentUserId(Integer userId);
	
	List<InternshipLog> findAllByInternshipInternshipId(Integer internshipId);

}
