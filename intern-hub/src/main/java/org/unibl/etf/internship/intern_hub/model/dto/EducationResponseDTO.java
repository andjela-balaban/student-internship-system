package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationResponseDTO {

	private Integer educationId;
	private String institution;
	private String degree;
	private LocalDate startDate;
	private LocalDate endDate;
}
