package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {

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
	
	@NotBlank(message = "First name is required and must not be blank")
	@Size(max = 40, message = "First name must have maximum 40 characters")
	private String firstName;
	
	@NotBlank(message = "Last name is required and must not be blank")
	@Size(max = 40, message = "Last name must have maximum 40 characters")
	private String lastName;
	
	@NotBlank(message = "Index is required and must not be blank")
	@Size(max = 15, message = "Index must have maximum 15 characters")
	private String studentIndex;
	
	@NotNull(message = "Date of birth is required")
	@Past(message = "Date of birth must be in the past")
	private LocalDate dateOfBirth;
	
	private String phoneNumber;
	
	@NotNull(message = "Faculty ID is required")
	private Integer facultyId;
	
}