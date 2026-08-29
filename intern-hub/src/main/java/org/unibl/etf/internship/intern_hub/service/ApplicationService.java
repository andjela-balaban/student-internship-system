package org.unibl.etf.internship.intern_hub.service;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.unibl.etf.internship.intern_hub.mapper.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.repository.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@Service
@Transactional
public class ApplicationService {
	@Autowired
	
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private ApplicationMapper applicationMapper;

	@Autowired
	private InternshipRepository internshipRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private InternshipLogService logService;
	
	@Transactional(readOnly = true)
	public List<ApplicationResponseDTO> getAllByInternshipId(Integer internshipId) {
		if(!internshipRepository.existsById(internshipId))
			throw new EntityNotFoundException("Internship with ID " + internshipId + " not found!");
		
		return applicationRepository.findAllByInternshipInternshipId(internshipId)
				.stream()
				.map(applicationMapper::toResponseDTO)
				.toList();
	}
	
	@Transactional(readOnly = true)
	public ApplicationResponseDTO getByInternshipIdAndApplicationId(Integer internshipId, Integer applicationId) {
		return applicationRepository.findByApplicationIdAndInternshipInternshipId(applicationId, internshipId)
				.map(applicationMapper::toResponseDTO)
				.orElseThrow(() -> new EntityNotFoundException("Application with ID " + applicationId + " for internship with ID " + internshipId + " not found!"));
	}
	
	
	public ApplicationResponseDTO createApplication(Integer internshipId, ApplicationRequestDTO request) {
		if(applicationRepository.existsByStudentUserIdAndInternshipInternshipId(request.getStudentId(), internshipId))
			throw new IllegalArgumentException("Student with ID " + request.getStudentId() + " has already applied for internship with ID " + internshipId + "!");
		
		Application entity = new Application();
		entity.setInternship(internshipRepository.findById(internshipId)
				.orElseThrow(() -> new EntityNotFoundException("Internship with ID " + internshipId + " not found!")));
		
		entity.setStatus(ApplicationStatus.PENDING);
		entity.setStudent(studentRepository.findById(request.getStudentId())
				.orElseThrow(() -> new EntityNotFoundException("Student with ID " + request.getStudentId() + " not found!")));
		
		
		return applicationMapper.toResponseDTO(applicationRepository.save(entity));
	}
	
	public ApplicationResponseDTO updateApplication(Integer internshipId, Integer applicationId, ApplicationStatusUpdateDTO request) {
		Application entity = applicationRepository.findByApplicationIdAndInternshipInternshipId(applicationId, internshipId)
				.orElseThrow(() -> new EntityNotFoundException("Internship application with ID " + applicationId + " for internship with ID " + internshipId + " not found!"));
		
		entity.setStatus(request.getStatus());
		
		if(entity.getStatus() == ApplicationStatus.ACCEPTED)
			logService.createNewLogForInternship(entity);
		
		return applicationMapper.toResponseDTO(applicationRepository.save(entity));
	}
	
	public void deleteApplication(Integer internshipId, Integer applicationId) {
		Application entity = applicationRepository.findByApplicationIdAndInternshipInternshipId(applicationId, internshipId)
				.orElseThrow(() -> new EntityNotFoundException("Internship application with ID " + applicationId + " for internship with ID " + internshipId + " not found!"));
		
		applicationRepository.delete(entity);
	}
}
