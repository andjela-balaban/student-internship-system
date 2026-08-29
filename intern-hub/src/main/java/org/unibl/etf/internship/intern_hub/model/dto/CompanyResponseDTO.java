package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDTO {

	private Integer companyId;
	private String username;
	private String email;
	private String name;
	private Boolean isActive = false;
}
