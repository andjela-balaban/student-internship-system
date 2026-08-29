package org.unibl.etf.internship.intern_hub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import java.util.*;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
	
	List<Student> findAllByFirstNameAndLastName(String firstName, String lastName);
	
	List<Student> findAllByFirstName(String firstName);

}
