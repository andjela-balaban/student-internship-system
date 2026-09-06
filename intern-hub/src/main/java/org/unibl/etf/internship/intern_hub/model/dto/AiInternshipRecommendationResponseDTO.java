package org.unibl.etf.internship.intern_hub.model.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AiInternshipRecommendationResponseDTO {
	private Integer internshipId;
	private String internshipName;
	private String description;
	private String companyName;
	private LocalDate start;
	private List<TechnologyResponseDTO> technologies;
	private double matchPercentage;
	private String reason;
	
}
