package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationRequestDTO {

	@NotBlank(message = "Institute is required and must not be blank")
	@Size(max = 50, message = "Institute must have maximum 50 characters")
	private String institution;
	
	@NotBlank(message = "Degree is required and must not be blank")
	@Size(max = 100, message = "Degree must have maximum 100 characters")
	private String degree;
	
	@PastOrPresent(message = "Start date cannot be in the future")
	private LocalDate startDate;
	
	private LocalDate endDate;
}
