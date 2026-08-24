package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

	@NotBlank(message = "Username is required and must not be blank.")
	@Size(max = 40, message = "Username must have maximum 40 characters")
	private String username;
	
	@NotBlank(message = "Password is required and must not be blank.")
	@Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
	private String password;
}
