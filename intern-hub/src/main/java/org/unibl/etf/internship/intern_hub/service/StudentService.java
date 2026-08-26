package org.unibl.etf.internship.intern_hub.service;

import org.unibl.etf.internship.intern_hub.mapper.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.repository.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.*;
import jakarta.persistence.*;
import java.util.*;

@Service
@Transactional
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FacultyRepository facultyRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private ApplicationMapper applicationMapper;
	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Transactional(readOnly = true)
	public List<StudentResponseDTO> getAll() {
		return studentRepository.findAll()
				.stream()
				.map(studentMapper::toResponseDTO)
				.toList();
	}
	
	@Transactional(readOnly = true)
	public StudentResponseDTO getById(Integer id) {
		Student entity = studentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Student with ID " + id + " not found!"));
	
		return studentMapper.toResponseDTO(entity);
	}
	
	public StudentResponseDTO createStudent(StudentRequestDTO request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new IllegalArgumentException("Username " + request.getUsername() + " is already taken.");
		}
		
		Faculty faculty = facultyRepository.findById(request.getFacultyId())
				.orElseThrow(() -> new EntityNotFoundException("Faculty with ID " + request.getFacultyId() + " not found!"));
		
		Student entity = studentMapper.toEntity(request);
		entity.setFaculty(faculty);
		entity.setPassword(passwordEncoder.encode(request.getPassword()));
		
		return studentMapper.toResponseDTO(studentRepository.save(entity));
		
	}
	
	public StudentResponseDTO updateStudent(Integer id, StudentRequestDTO request) {
		Student entity = studentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Student with ID " + id + " not found!"));
		
		Faculty faculty = facultyRepository.findById(request.getFacultyId())
				.orElseThrow(() -> new EntityNotFoundException("Faculty with ID " + request.getFacultyId() + " not found!"));
		
		entity.setFirstName(request.getFirstName());
		entity.setLastName(request.getLastName());
		entity.setEmail(request.getEmail());
		entity.setDateOfBirth(request.getDateOfBirth());
		entity.setPhoneNumber(request.getPhoneNumber());
		entity.setFaculty(faculty);
        entity.setStudentIndex(request.getStudentIndex());
		
		if(request.getPassword() != null && !request.getPassword().isBlank()) {
			entity.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		
		return studentMapper.toResponseDTO(studentRepository.save(entity));
	}
	
	public void deleteStudent(Integer id) {
		Student entity = studentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Student with ID " + id + " not found!"));
		
		studentRepository.delete(entity);
	}
	
	
	@Transactional(readOnly = true)
	public List<ApplicationResponseDTO> getStudentApplications(Integer id) {
		studentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Student with ID " + id + " not found!"));
		
		return applicationRepository.findAllByStudentUserId(id)
				.stream()
				.map(applicationMapper::toResponseDTO)
				.toList();
	}
	
	@Transactional(readOnly = true)
	public List<StudentResponseDTO> getAllByFullName(String fullName) {
		String parts[] = fullName.trim().split("\\s+");
		
		if(parts.length == 1) {
			return studentRepository.findAllByFirstName(fullName)
					.stream()
					.map(studentMapper::toResponseDTO)
					.toList();
		} else {
			return studentRepository.findAllByFirstNameAndLastName(parts[0], parts[1])
					.stream()
					.map(studentMapper::toResponseDTO)
					.toList();
		}
	}
	
	
	public void createFromCSV(MultipartFile file, Integer facultyId) throws Exception {
		//TODO
	}
}
