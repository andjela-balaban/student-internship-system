package org.unibl.etf.internship.intern_hub.service;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.unibl.etf.internship.intern_hub.mapper.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.repository.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class CvService {

	@Autowired
	private CvRepository  cvRepository;
	
	@Autowired 
	private CvMapper cvMapper;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired 
	private TechnologyRepository technologyRepository;
	
	@Autowired
	private ExperienceMapper experienceMapper;
	
	@Autowired
	private EducationMapper educationMapper;
	
	@Transactional(readOnly = true)
	public List<CvResponseDTO> getAll() {
		return cvRepository.findAll()
				.stream()
				.map(cvMapper::toResponseDTO)
				.toList();
	}
	
	@Transactional(readOnly = true)
	public CvResponseDTO getById(Integer id) {
		return cvRepository.findById(id)
				.map(cvMapper::toResponseDTO)
				.orElseThrow(() -> new EntityNotFoundException("CV with ID " + id + " not found!"));
	}
	
	@Transactional(readOnly = true)
	public byte[] getCvImage(Integer studentId) {
		Cv cv = cvRepository.findByStudentUserId(studentId)
				.orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " does not have a CV!"));
		
//		if(cv.getImage() == null)
//			throw new EntityNotFoundException("Student with ID " + studentId + " does not have an image in their CV!");
		
		if (cv.getImage() == null || cv.getImage().length == 0) {
	        return null;
	    }
		
		return cv.getImage();
	}
	
	@Transactional(readOnly = true)
	public CvResponseDTO getCvByStudentId(Integer studentId) {
		return cvRepository.findByStudentUserId(studentId)
				.map(cvMapper::toResponseDTO)
				.orElseThrow(() -> new EntityNotFoundException("CV for student with ID " + studentId + " not found!"));
	}
	
	
	public CvResponseDTO createCv(Integer studentId, CvRequestDTO cv, MultipartFile imageFile) throws IOException {
		if(cvRepository.existsByStudentUserId(studentId))
			throw new IllegalArgumentException("Student with ID " + studentId + " already has a CV!");
		
		Cv entity = cvMapper.toEntity(cv);
		
		entity.setStudent(
				studentRepository.findById(studentId)
				.orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " not found!")));;
		
		if(imageFile != null && !imageFile.isEmpty()) {
			entity.setImage(imageFile.getBytes());
		}
		
		List<Technology> technologies = new ArrayList<>();
		for(Integer technologyId : cv.getTechnologyIds()) {
			technologies.add(technologyRepository.findById(technologyId)
					.orElseThrow(() -> new EntityNotFoundException("Technology with ID " + technologyId + " not found!")));
		}
		entity.setTechnologies(technologies);
		
		if(entity.getEducation() != null)
			for(Education education : entity.getEducation())
				education.setCv(entity);
		else entity.setEducation(new ArrayList<>());
		
		
		if(entity.getExperience() != null)
			for(Experience experience : entity.getExperience())
				experience.setCv(entity);
		else entity.setExperience(new ArrayList<>());
		
		entity = cvRepository.save(entity);
		
		return cvMapper.toResponseDTO(cvRepository.save(entity));
	}
			
	
	public CvResponseDTO updateCv(Integer studentId, CvRequestDTO cv, MultipartFile imageFile) throws IOException {
		Cv entity = cvRepository.findByStudentUserId(studentId)
				.orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " does not have a CV!"));
		
		cvMapper.updateCvFromDTO(cv, entity);
		
		if(imageFile != null && !imageFile.isEmpty()) {
			entity.setImage(imageFile.getBytes());
		}
		
		List<Technology> technologies = new ArrayList<>();
		for(Integer technologyId : cv.getTechnologyIds()) {
			technologies.add(technologyRepository.findById(technologyId)
					.orElseThrow(() -> new EntityNotFoundException("Technology with ID " + technologyId + " not found!")));
		}
		entity.setTechnologies(technologies);
		
		entity.getEducation().clear();
		if(cv.getEducation() != null) {
			for(EducationRequestDTO e : cv.getEducation()) {
				Education education = educationMapper.toEntity(e);
				education.setCv(entity);
				
				entity.getEducation().add(education);
			}			
		}
		
		entity.getExperience().clear();
		if(cv.getExperience() != null) {
			for(ExperienceRequestDTO e : cv.getExperience()) {
				Experience experience = experienceMapper.toEntity(e);
				experience.setCv(entity);
				
				entity.getExperience().add(experience);			
			}
		}
	
		return cvMapper.toResponseDTO(cvRepository.save(entity));
	}
	
	public void deleteCv(Integer studentId) {
		Cv entity = cvRepository.findByStudentUserId(studentId)
				.orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " does not have a CV!"));
		
		if (entity.getStudent() != null) {
	        entity.getStudent().setCv(null); 
	        entity.setStudent(null);
	    }
		
		cvRepository.delete(entity);
	}
	
	public void uploadCvImage(Integer studentId, MultipartFile imageFile) throws IOException {
	    if (imageFile == null || imageFile.isEmpty()) {
	        throw new IllegalArgumentException("Image file cannot be empty!");
	    }

	    Cv cv = cvRepository.findByStudentUserId(studentId)
	            .orElseThrow(() -> new EntityNotFoundException("CV for student with ID " + studentId + " not found!"));

	    cv.setImage(imageFile.getBytes());
	    cvRepository.save(cv);
	}
	
	
}
