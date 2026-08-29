package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyRequestDTO {
	
	@NotBlank(message = "Technology name is required and must not be blank")
	@Size(max = 30, message = "Name must have maximum 30 characters")
	private String name;
}