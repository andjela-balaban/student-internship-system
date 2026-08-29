package org.unibl.etf.internship.intern_hub.model.dto.filter;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentFilterDTO {
	private String fullName;
	private Integer facultyId;
}
