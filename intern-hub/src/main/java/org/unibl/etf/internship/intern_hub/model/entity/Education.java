package org.unibl.etf.internship.intern_hub.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "education")

public class Education {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer educationId;
	
	@Column(nullable = false, length = 50)
	private String institution;
	
	@Column(length = 100)
	private String degree;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@ManyToOne
	@JoinColumn(name = "cvId", nullable = false)
	private Cv cv;
	
}
