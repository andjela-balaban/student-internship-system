package org.unibl.etf.internship.intern_hub.specification;

import org.unibl.etf.internship.intern_hub.model.dto.filter.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.springframework.data.jpa.domain.*;
import jakarta.persistence.criteria.*;
import java.util.*;

public class StudentSpecification {
	public static Specification<Student> getStudentsByFilter(StudentFilterDTO filter) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if(filter.getFullName() != null && !filter.getFullName().isBlank()) {
				predicates.add(cb.like(cb.lower(cb.concat(cb.concat(root.get("firstName"), " "), root.get("lastName"))), "%" + filter.getFullName().toLowerCase() + "%"));
			}
			
			if(filter.getFacultyId() != null) {
				predicates.add(cb.equal(root.get("faculty").get("userId"), filter.getFacultyId()));
			}
			
			return cb.and(predicates);
		};
	}
}