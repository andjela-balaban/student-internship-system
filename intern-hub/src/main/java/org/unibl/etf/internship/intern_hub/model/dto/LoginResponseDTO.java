package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

	private String token;
	private Integer userId;
	private String role;
	private String username;
}
