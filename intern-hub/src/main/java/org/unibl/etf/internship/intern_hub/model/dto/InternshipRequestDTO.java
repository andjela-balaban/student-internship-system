package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.util.*;
import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternshipRequestDTO {

	@NotBlank(message = "Internship name is required and must not be blank")
	@Size(max = 50, message = "Name must have maximum 50 characters")
	private String name;
	
	@NotBlank(message = "Internship description is required and must not be blank")
	@Size(max = 500, message = "Description must have maximum 500 characters")
	private String description;
	
	@FutureOrPresent(message = "Internship start date must be in present or future")
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@Size(max = 500, message = "Terms and conditions must have maximum 500 characters")
	private String termsAndConditions;
	
	@NotNull(message = "Company ID must not be null")
	private Integer companyId;
	
	private List<Integer> technologyIds;
}
