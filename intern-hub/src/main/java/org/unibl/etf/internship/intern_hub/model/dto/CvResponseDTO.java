package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CvResponseDTO {

	private Integer cvId;
	private String interests;
	private String address;
	private StudentResponseDTO student;
	private List<EducationRequestDTO> education;
	private List<ExperienceRequestDTO> experience;
	private List<TechnologyResponseDTO> technologies;
}
