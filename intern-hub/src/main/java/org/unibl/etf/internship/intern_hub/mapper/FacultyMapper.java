package org.unibl.etf.internship.intern_hub.mapper;

import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FacultyMapper {

	@Mapping(target = "userId", ignore = true)
	Faculty toEntity(FacultyRequestDTO dto);

	@Mapping(source = "userId", target = "facultyId")
	FacultyResponseDTO toResponseDTO(Faculty faculty);
	
}
