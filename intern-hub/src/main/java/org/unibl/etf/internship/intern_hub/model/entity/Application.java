package org.unibl.etf.internship.intern_hub.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application")
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer applicationId;
	
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status = ApplicationStatus.PENDING;
	
	@ManyToOne
	@JoinColumn(name = "studentId", nullable = false)
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "internshipId", nullable = false)
	private Internship internship;
}
