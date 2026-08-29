package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import java.time.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternshipResponseDTO {

	private Integer internshipId;
	private String name;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private String termsAndConditions;
	private CompanyResponseDTO company;
	private List<TechnologyResponseDTO> technologies;
}
