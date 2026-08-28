package org.unibl.etf.internship.intern_hub.mapper;

import org.mapstruct.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;

@Mapper(componentModel = "spring")
public interface EducationMapper {
	
	EducationResponseDTO toResponseDTO(Education education);
	
	@Mapping(target = "educationId", ignore = true)
	@Mapping(target = "cv", ignore = true)
	Education toEntity(EducationRequestDTO dto);
	
}
