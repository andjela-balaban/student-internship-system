package org.unibl.etf.internship.intern_hub.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "experience")
public class Experience {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer experienceId;

	@Column(nullable = false, length = 50)
	private String company;
	
	@Column(length = 100)
	private String position;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@ManyToOne
	@JoinColumn(name = "cvId", nullable = false)
	private Cv cv;
}
