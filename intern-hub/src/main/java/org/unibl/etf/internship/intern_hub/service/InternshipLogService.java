package org.unibl.etf.internship.intern_hub.service;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.unibl.etf.internship.intern_hub.mapper.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.dto.filter.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.specification.*;
import org.unibl.etf.internship.intern_hub.repository.*;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
@Transactional
public class InternshipLogService {

	@Autowired
	private InternshipLogRepository logRepository;
	
	@Autowired
	private InternshipLogMapper logMapper;
	
	@Autowired
	private InternshipRepository internshipRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	
	@Transactional(readOnly = true)
	public List<InternshipLogResponseDTO> getAll(InternshipLogFilterDTO filter) {
		Specification<InternshipLog> specification = InternshipLogSpecification.getLogsByFilter(filter);
		
		return logRepository.findAll(specification)
				.stream()
				.map(logMapper::toResponseDTO)
				.toList();
	}
	
	@Transactional(readOnly = true)
	public InternshipLogResponseDTO getById(Integer logId) {
		return logRepository.findById(logId)
				.map(logMapper::toResponseDTO)
				.orElseThrow(() -> new EntityNotFoundException("Log with ID " + logId + " not found!"));
	}
	
	public List<InternshipLogResponseDTO> getAllLogsForStudent(Integer studentId) {
		if(!studentRepository.existsById(studentId))
			throw new EntityNotFoundException("Student with ID " + studentId + " not found!");
		
		return logRepository.findAllByStudentUserId(studentId)
				.stream()
				.map(logMapper::toResponseDTO)
				.toList();
	}
	
	public List<InternshipLogResponseDTO> getAllLogsForInternship(Integer internshipId) {
		if(!internshipRepository.existsById(internshipId))
			throw new EntityNotFoundException("Internship with ID " + internshipId + " not found!");
		
		return logRepository.findAllByInternshipInternshipId(internshipId)
				.stream()
				.map(logMapper::toResponseDTO)
				.toList();
	}
	
	public InternshipLogResponseDTO createNewLogForInternship(Application application) {
		InternshipLog log = new InternshipLog();
		log.setFaculty(application.getStudent().getFaculty());
		log.setInternship(application.getInternship());
		log.setStudent(application.getStudent());
		log.setContent("");
		
		return logMapper.toResponseDTO(logRepository.save(log));
	}
	
	public void updateStudentLogContent(Integer logId, StudentLogUpdateDTO dto) {
		InternshipLog log = logRepository.findById(logId)
				.orElseThrow(() -> new EntityNotFoundException("Log with ID " + logId + " not found!"));
		
		
		String content = log.getContent();
		if(content == null)
			content = "";
		
		if(log.getLastLogEntry() != null) {
			WeekFields weekFields = WeekFields.of(Locale.getDefault());
	        
	        boolean isSameWeek = LocalDateTime.now().get(weekFields.weekOfYear()) == log.getLastLogEntry().get(weekFields.weekOfYear());
	        boolean isSameYear = LocalDateTime.now().get(weekFields.weekBasedYear()) == log.getLastLogEntry().get(weekFields.weekBasedYear());
	        
	        if (isSameWeek && isSameYear) {
	            throw new IllegalArgumentException("Log entry for this week has already been submitted!");
	        }	
		}
		
		log.setContent(content + "\n" + dto.getContent());
		log.setLastLogEntry(LocalDateTime.now());
			
	}
	
	public void reviewLogByFaculty(Integer logId, FacultyReviewLogDTO dto) {
		InternshipLog log = logRepository.findById(logId)
				.orElseThrow(() -> new EntityNotFoundException("Log with ID " + logId + " not found!"));
		
		if(log.getGrade() != null) {
			throw new IllegalArgumentException("This log has already been graded!");
		}
		
//		if(log.getFacultyComment() != null) {
//			throw new IllegalArgumentException("Comment has already been submitted for this log!");
//		}
		
		log.setGrade(dto.getGrade());
		log.setFacultyComment(dto.getFacultyComment());
	}
	
	public void reviewLogByCompany(Integer logId, CompanyReviewLogDTO dto) {
		InternshipLog log = logRepository.findById(logId)
				.orElseThrow(() -> new EntityNotFoundException("Log with ID " + logId + " not found!"));
		
		if(log.getCompanyComment() != null) {
			throw new IllegalArgumentException("Comment has already been submitted for this log!");
		}
		
		log.setCompanyComment(dto.getCompanyComment());
	}
}
