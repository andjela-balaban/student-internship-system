package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternshipLogResponseDTO {

	private Integer logId;
	
	private StudentResponseDTO student;
	
	private InternshipResponseDTO internship;
	
	private FacultyResponseDTO faculty;
	
	private String content;
	
	private String companyComment;
	
	private String facultyComment;
	
	private LocalDateTime lastLogEntry;
	
	private Integer grade;
	
}
