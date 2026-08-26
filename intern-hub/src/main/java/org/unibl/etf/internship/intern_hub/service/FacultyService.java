package org.unibl.etf.internship.intern_hub.service;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.unibl.etf.internship.intern_hub.mapper.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.repository.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.*;

@Service
@Transactional
public class FacultyService {

	@Autowired
	private FacultyRepository facultyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FacultyMapper facultyMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public List<FacultyResponseDTO> getAll() {
		return facultyRepository.findAll()
				.stream()
				.map(facultyMapper::toResponseDTO)
				.toList();
	}
	
	@Transactional(readOnly = true)
	public FacultyResponseDTO getById(Integer id) {
		return facultyRepository.findById(id)
				.map(facultyMapper::toResponseDTO)
				.orElseThrow(() -> new EntityNotFoundException("Faculty with ID " + id + " not found!"));
    }
	
	public FacultyResponseDTO createFaculty(FacultyRequestDTO request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new IllegalArgumentException("Username " + request.getUsername() + " is already taken!");
		}
		
		Faculty entity = facultyMapper.toEntity(request);
		entity.setPassword(passwordEncoder.encode(request.getPassword()));
		
		return facultyMapper.toResponseDTO(facultyRepository.save(entity));
	}
	
	public FacultyResponseDTO updateFaculty(Integer id, FacultyRequestDTO request) {
		Faculty entity = facultyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Faculty with ID " + id + " not found!"));
		
		
		entity.setName(request.getName());
		entity.setEmail(request.getEmail());
		
		if(request.getPassword() != null && !request.getPassword().isBlank()) {
			entity.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		
		return facultyMapper.toResponseDTO(facultyRepository.save(entity));
	}
	
	public void deleteFaculty(Integer id) {
		Faculty faculty = facultyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Faculty with ID " + id + " not found!"));
		
		facultyRepository.delete(faculty);
	}
	
	
}
