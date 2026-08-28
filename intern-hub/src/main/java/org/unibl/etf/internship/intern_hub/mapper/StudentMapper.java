package org.unibl.etf.internship.intern_hub.mapper;

import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {FacultyMapper.class})
public interface StudentMapper {

	@Mapping(target = "faculty", ignore = true)
	@Mapping(target = "userId", ignore = true)
	@Mapping(target = "cv", ignore = true)
	Student toEntity(StudentRequestDTO dto);
	
	@Mapping(source = "userId", target = "studentId")
	StudentResponseDTO toResponseDTO(Student student);
	
	@Mapping(target = "faculty", ignore = true)
	@Mapping(target = "userId", ignore = true)
	@Mapping(target = "cv", ignore = true)
	Student toEntity(CsvStudentDTO dto);
	
}
