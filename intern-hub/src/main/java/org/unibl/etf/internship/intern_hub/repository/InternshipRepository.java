package org.unibl.etf.internship.intern_hub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Integer> {

}
