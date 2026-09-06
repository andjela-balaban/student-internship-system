package org.unibl.etf.internship.intern_hub.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiRequestDTO {
	private List<InternshipResponseDTO> internships;
	private StudentResponseDTO student;
	private CvResponseDTO cv;
}