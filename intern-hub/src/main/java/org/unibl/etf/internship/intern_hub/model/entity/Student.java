package org.unibl.etf.internship.intern_hub.model.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "studentId")
public class Student extends User {
	
	@Column(nullable = false, length = 40)
	private String firstName;
	
	@Column(nullable = false, length = 40)
	private String lastName;
	
	@Column(nullable = false, length = 15)
	private String studentIndex;
	
	@Column(nullable = false)
	private LocalDate dateOfBirth;
	
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(name = "facultyId", nullable = false)
	private Faculty faculty;
	
	@OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private Cv cv;
	
}
