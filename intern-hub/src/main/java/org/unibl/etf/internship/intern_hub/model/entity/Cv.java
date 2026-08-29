package org.unibl.etf.internship.intern_hub.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cv")
public class Cv {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cvId;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] image;
	
	@Column(nullable = false, length = 150)
	private String interests;
	
	private String address;
	
	@OneToOne
	@JoinColumn(name = "studentId", unique = true, nullable = false)
	private Student student;
	
	@OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Education> education;
	
	@OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Experience> experience;
	
	@ManyToMany
	@JoinTable(
			name = "known_technology",
			joinColumns = @JoinColumn(name = "cvId"),
			inverseJoinColumns = @JoinColumn(name = "technologyId")
			)
	private List<Technology> technologies;
	
}
