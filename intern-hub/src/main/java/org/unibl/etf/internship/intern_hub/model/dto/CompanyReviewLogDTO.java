package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyReviewLogDTO {

	@NotBlank(message = "Comapany comment is required and must not be blank")
	private String companyComment;
	
	@NotBlank(message = "Faculty comment is required and must not be blank")
	private String facultyComment;
}
