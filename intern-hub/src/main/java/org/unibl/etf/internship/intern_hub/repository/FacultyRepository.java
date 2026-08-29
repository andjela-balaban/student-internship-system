package org.unibl.etf.internship.intern_hub.repository;

import org.springframework.data.jpa.repository.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.springframework.stereotype.*;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer>{

}
