package org.unibl.etf.internship.intern_hub.mapper;

import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
	
	@Mapping(target = "userId", ignore = true)
	Company toEntity(CompanyRequestDTO dto);
	
	@Mapping(source = "userId", target = "companyId")
	CompanyResponseDTO toResponseDTO(Company company);

}
