package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiRecommendationResultDTO {
	private Integer internshipId;
	private double matchPercentage;
	private String reason;
}
