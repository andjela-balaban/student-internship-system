package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusUpdateDTO {

	@NotNull(message = "Status is required and must not be null")
	private ApplicationStatus status;
}
