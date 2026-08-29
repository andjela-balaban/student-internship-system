package org.unibl.etf.internship.intern_hub.mapper;

import org.mapstruct.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;

@Mapper(componentModel = "spring", uses = { StudentMapper.class, FacultyMapper.class, InternshipMapper.class })
public interface InternshipLogMapper {
	
	InternshipLogResponseDTO toResponseDTO(InternshipLog log);
	
}

