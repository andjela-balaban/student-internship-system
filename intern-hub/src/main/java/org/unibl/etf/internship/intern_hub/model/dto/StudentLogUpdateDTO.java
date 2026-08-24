package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentLogUpdateDTO {

	@NotNull(message = "Content must not be blank")
	private String content;
}
