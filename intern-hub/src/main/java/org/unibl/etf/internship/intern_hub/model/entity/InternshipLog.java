package org.unibl.etf.internship.intern_hub.model.entity;

import jakarta.persistence.*;
import java.time.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "internship_log")
public class InternshipLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer logId;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;
	
	private String companyComment;
	
	private String facultyComment;
	
	private LocalDateTime lastLogEntry;

	private Integer grade;
	
	@ManyToOne
	@JoinColumn(name = "studentId", nullable = false)
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "intershipId", nullable = false)
	private Internship internship;
	
	@ManyToOne
	@JoinColumn(name = "facultyId", nullable = false)
	private Faculty faculty;
}
