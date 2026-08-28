package org.unibl.etf.internship.intern_hub.mapper;

import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TechnologyMapper {

	@Mapping(target = "technologyId", ignore = true)
	Technology toEntity(TechnologyRequestDTO dto);

	TechnologyResponseDTO toResponseDTO(Technology entity);
}
