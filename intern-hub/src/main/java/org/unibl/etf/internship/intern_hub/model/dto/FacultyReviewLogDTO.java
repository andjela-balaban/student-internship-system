package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyReviewLogDTO {

	@NotNull(message = "Grade is required and must not be null")
//	@Min(value = 5, message = "Grade must be at least 5")
//	@Max(value = 10, message = "Grade must be at most 10")
	@Range(min = 5, max = 10, message = "Grade must be between 5 and 10")
	private Integer grade;
	
	@NotBlank(message = "Faculty comment is required and must not be blank")
	private String facultyComment;
}
