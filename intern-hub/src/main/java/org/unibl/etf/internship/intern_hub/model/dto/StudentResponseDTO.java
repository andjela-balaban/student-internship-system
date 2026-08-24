package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {

	private Integer studentId;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private String studentIndex;
	private LocalDate dateOfBirth;
	private String phoneNumber;
	private FacultyResponseDTO faculty;
}
