package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyResponseDTO {
	
	private Integer facultyId;
	private String username;
	private String email;
	private String name;
}
