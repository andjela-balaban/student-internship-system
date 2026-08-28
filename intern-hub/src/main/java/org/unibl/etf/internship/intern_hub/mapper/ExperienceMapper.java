package org.unibl.etf.internship.intern_hub.mapper;

import org.mapstruct.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {

	ExperienceResponseDTO toResponseDTO(Experience experience);
	
	@Mapping(target = "experienceId", ignore = true)
	@Mapping(target = "cv", ignore = true)
	Experience toEntity(ExperienceRequestDTO dto);
	
}
