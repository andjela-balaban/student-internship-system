package org.unibl.etf.internship.intern_hub.service;

import org.unibl.etf.internship.intern_hub.mapper.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.repository.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.*;
import java.util.*;

@Service
@Transactional
public class TechnologyService {

	@Autowired
	private TechnologyRepository technologyRepository;
	
	@Autowired
	private TechnologyMapper technologyMapper;
	
	@Transactional(readOnly = true)
	public List<TechnologyResponseDTO> getAll() {
		return technologyRepository.findAll()
				.stream()
				.map(technologyMapper::toResponseDTO)
				.toList();
	}
	
	@Transactional(readOnly = true)
	public TechnologyResponseDTO getById(Integer id) {
		return technologyRepository.findById(id)
				.map(technologyMapper::toResponseDTO)
				.orElseThrow(() -> new EntityNotFoundException("Technology with ID: " + id + " not found!"));
	}
	
	public TechnologyResponseDTO createTechnology(TechnologyRequestDTO request) {
		if(technologyRepository.existsByName(request.getName()))
			throw new IllegalArgumentException("Technology with name " + request.getName() + " already exists!");
		
		Technology entity = technologyMapper.toEntity(request);
		return technologyMapper.toResponseDTO(technologyRepository.save(entity));
		
	}
	
	public TechnologyResponseDTO updateTechnology(Integer id, TechnologyRequestDTO request) {
		Technology entity = technologyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Technology with ID " + id + " not found!"));
		
		entity.setName(request.getName());
		
		return technologyMapper.toResponseDTO(technologyRepository.save(entity));
	}
	
	
	public void deleteTechnology(Integer id) {
		Technology entity = technologyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Technology with ID " + id + " not found!"));
		
		technologyRepository.delete(entity);
	}
	
}
