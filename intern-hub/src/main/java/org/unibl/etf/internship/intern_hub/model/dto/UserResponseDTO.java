package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

	private Integer userId;
	private String username;
	private String email;
}
