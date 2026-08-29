package org.unibl.etf.internship.intern_hub.service;

import org.unibl.etf.internship.intern_hub.mapper.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.repository.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.*;
import jakarta.persistence.*;
import java.util.*;

@Service
@Transactional
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public List<CompanyResponseDTO> getAll() {
		return companyRepository.findAll()
				.stream()
				.map(companyMapper::toResponseDTO)
				.toList();
	}
	
	@Transactional(readOnly = true)
	public CompanyResponseDTO getById(Integer id) {
		return companyRepository.findById(id)
				.map(companyMapper::toResponseDTO)
				.orElseThrow(() -> new EntityNotFoundException("Company with ID " + id + " not found!"));
	}
	
	public CompanyResponseDTO createCompany(CompanyRequestDTO request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new IllegalArgumentException("Username " + request.getUsername() + " is already taken.");
		}
		
		Company entity = companyMapper.toEntity(request);
		entity.setPassword(passwordEncoder.encode(request.getPassword()));
		
		if(entity.getIsActive() == null)
			entity.setIsActive(true);
		
		return companyMapper.toResponseDTO(companyRepository.save(entity));
	}
	
	public CompanyResponseDTO updateCompany(Integer id, CompanyRequestDTO request) {
		Company entity = companyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Company with ID " + id + " not found!"));
		
		entity.setName(request.getName());
		entity.setEmail(request.getEmail());
		
		if(request.getIsActive() != null)
			entity.setIsActive(request.getIsActive());
		
		if(request.getPassword() != null && !request.getPassword().isBlank() ) 
			entity.setPassword(passwordEncoder.encode(request.getPassword()));
		
		return companyMapper.toResponseDTO(companyRepository.save(entity));
	}
	
	public void deleteCompany(Integer id) {
		Company entity = companyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Company with ID " + id + " not found!"));
		
		companyRepository.delete(entity);
	}
	
	public CompanyResponseDTO updateStatus(Integer id, CompanyStatusUpdateDTO newStatus) {
		Company entity = companyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Company with ID " + id + " not found!"));
		
		entity.setIsActive(newStatus.getIsActive());
		
		return companyMapper.toResponseDTO(companyRepository.save(entity));
	}
	
}
