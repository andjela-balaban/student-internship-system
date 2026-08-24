package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequestDTO {

	@NotNull(message = "Student ID must not be null")
	private Integer studentId;
}
