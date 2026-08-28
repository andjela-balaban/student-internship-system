package org.unibl.etf.internship.intern_hub.specification;

import org.unibl.etf.internship.intern_hub.model.dto.filter.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.springframework.data.jpa.domain.*;
import jakarta.persistence.criteria.*;
import java.util.*;

public class InternshipLogSpecification {
	public static Specification<InternshipLog> getLogsByFilter(InternshipLogFilterDTO filter) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if(filter.getCompanyId() != null)
				predicates.add(cb.equal(root.get("internship").get("company").get("userId"), filter.getCompanyId()));
			
			if(filter.getFacultyId() != null) 
				predicates.add(cb.equal(root.get("faculty").get("userId"), filter.getFacultyId()));
			
			return cb.and(predicates);
		};
	}
}
