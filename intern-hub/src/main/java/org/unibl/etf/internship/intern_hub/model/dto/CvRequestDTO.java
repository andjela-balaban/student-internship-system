package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CvRequestDTO {

	@NotBlank(message = "Interests are required and must not be blank")
	@Size(max = 150, message = "Interests must have maximum 150 characters")
	private String interests;
	
	private String address;
	
	@Valid
	private List<EducationRequestDTO> education;
	
	@Valid
	private List<ExperienceRequestDTO> experience;
	
	private List<Integer> technologyIds;
}
