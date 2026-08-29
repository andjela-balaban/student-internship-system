package org.unibl.etf.internship.intern_hub.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import java.time.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "internship")
public class Internship {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer internshipId;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false, length = 500)
	private String description;
	
	private LocalDate startDate;
    
	private LocalDate endDate;
	
	@Column(length = 500)
    private String termsAndConditions;
	
	@ManyToOne
	@JoinColumn(name = "companyId", nullable = false)
	private Company company;
	
	@ManyToMany
	@JoinTable(
			name = "use_technology",
			joinColumns = @JoinColumn(name = "internshipId"),
			inverseJoinColumns = @JoinColumn(name = "technologyId")
			)
	private List<Technology> technologies;
	
	@OneToMany(mappedBy = "internship", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Application> applications;
	
	@OneToMany(mappedBy = "internship", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<InternshipLog> logs;
	
}
