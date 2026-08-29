package org.unibl.etf.internship.intern_hub.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDTO {
	@NotBlank(message = "Username is required and must not be blank")
	@Size(max = 40, message = "Username must have maximum 40 characters")
	private String username;
	
	@NotBlank(message = "Email is required and must not be blank")
	@Email(message = "Email needs to be in valid format")
	@Size(max = 50, message = "Email must have maximum 50 characters")
	private String email;
	
	@NotBlank(message = "Password is required and must not be blank")
	@Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
	private String password;
	
	@NotBlank(message = "Company name is required and must not be blank")
	@Size(max = 50, message = "Company name must have maximum 50 characters")
	private String name;
	
	private Boolean isActive = false;
}
