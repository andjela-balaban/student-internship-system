package org.unibl.etf.internship.intern_hub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Integer> {

	boolean existsByNameAndTechnologyIdNot(String name, Integer technologyId);
	
	boolean existsByName(String name);
	
}
