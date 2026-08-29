package org.unibl.etf.internship.intern_hub.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "company")
@PrimaryKeyJoinColumn(name = "companyId")
public class Company extends User {

	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false)
	private Boolean isActive;
}
