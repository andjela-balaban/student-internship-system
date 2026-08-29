package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import java.time.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceRequestDTO {

	@NotBlank(message = "Company is required and must not be blank")
	@Size(max = 50, message = "Company must have maximum 50 characters")
	private String company;

	@NotBlank(message = "Position is required and must not be blank")
	@Size(max = 100, message = "Position must have maximum 100 characters")
	private String position;
	
	
	@PastOrPresent(message = "Start date cannot be in the future")
	private LocalDate startDate;
	private LocalDate endDate;
}
