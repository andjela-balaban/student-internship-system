package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDTO {

	@NotBlank(message = "Old password is required and must not be blank.")
	@Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
	private String oldPassword;
	
	@NotBlank(message = "New password is required and must not be blank.")
	@Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
	private String newPassword;
	
	@NotBlank(message = "New password confirm is required and must not be blank.")
	@Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
	private String newPasswordConfirm;
}
