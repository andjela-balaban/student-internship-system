package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseDTO {
	
	private Integer applicationId;
	private ApplicationStatus status;
	private StudentResponseDTO student;
	private InternshipResponseDTO internship;
}
