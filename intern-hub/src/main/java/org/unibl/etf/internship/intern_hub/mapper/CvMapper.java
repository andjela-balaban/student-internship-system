package org.unibl.etf.internship.intern_hub.mapper;

import org.mapstruct.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;

@Mapper(componentModel = "spring", uses = { EducationMapper.class, ExperienceMapper.class, TechnologyMapper.class, StudentMapper.class })
public interface CvMapper {
	
	CvResponseDTO toResponseDTO(Cv cv);
	
	@Mapping(target = "cvId", ignore = true)
	@Mapping(target = "student", ignore = true)
	@Mapping(target = "image", ignore = true)
	@Mapping(target = "technologies", ignore = true)
	Cv toEntity(CvRequestDTO dto);
	
	@Mapping(target = "cvId", ignore = true)
	@Mapping(target = "student", ignore = true)
	@Mapping(target = "education", ignore = true)
	@Mapping(target = "experience", ignore = true)
	@Mapping(target = "technologies", ignore = true)
	@Mapping(target = "image", ignore = true)
	void updateCvFromDTO(CvRequestDTO dto, @MappingTarget Cv entity);
	
}