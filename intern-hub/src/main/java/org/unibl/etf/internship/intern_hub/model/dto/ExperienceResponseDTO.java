package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceResponseDTO {

	private Integer experienceId;
	private String company;
	private String position;
	private LocalDate startDate;
	private LocalDate endDate;
}
