package org.unibl.etf.internship.intern_hub.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "faculty")
@PrimaryKeyJoinColumn(name = "facultyId")
public class Faculty extends User {
	
	@Column(nullable = false, length = 50)
	private String name;
	
}
