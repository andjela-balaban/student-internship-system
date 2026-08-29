package org.unibl.etf.internship.intern_hub.mapper;

import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, InternshipMapper.class})
public interface ApplicationMapper {

	ApplicationResponseDTO toResponseDTO(Application application);
}
