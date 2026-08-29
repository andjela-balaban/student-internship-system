package org.unibl.etf.internship.intern_hub.specification;

import org.unibl.etf.internship.intern_hub.model.dto.filter.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.springframework.data.jpa.domain.*;
import jakarta.persistence.criteria.*;
import java.util.*;

public class InternshipSpecification {
	public static Specification<Internship> getInternshipsByFilter(InternshipFilterDTO filter) {
		return (root, Query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if(filter.getCompanyId() != null)
				predicates.add(cb.equal(root.get("company").get("userId"), filter.getCompanyId()));
			
			return cb.and(predicates);
		};
	}
}
