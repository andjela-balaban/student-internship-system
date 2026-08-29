package org.unibl.etf.internship.intern_hub.mapper;

import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {TechnologyMapper.class, CompanyMapper.class})
public interface InternshipMapper {

	@Mapping(target = "internshipId", ignore = true)
	@Mapping(target = "company", ignore = true)
	@Mapping(target = "technologies", ignore = true)
	Internship toEntity(InternshipRequestDTO dto);

	InternshipResponseDTO toResponseDTO(Internship internship);
}
