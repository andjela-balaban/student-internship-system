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
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InternshipService {
	@Autowired
	private InternshipRepository internshipRepository;
	
	@Autowired
	private InternshipMapper internshipMapper;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private TechnologyRepository technologyRepository;
	
//	@Autowired
//	private StudentService studentService;
//	
//	@Autowired
//	private CvService cvService;
	
	@Transactional(readOnly = true)
	public List<InternshipResponseDTO> getAll() {
		return internshipRepository.findAll()
				.stream()
				.map(internshipMapper::toResponseDTO)
				.toList();
	}
	
	@Transactional(readOnly = true)
	public InternshipResponseDTO getById(Integer id) {
		return internshipRepository.findById(id)
				.map(internshipMapper::toResponseDTO)
				.orElseThrow(() -> new EntityNotFoundException("Intership with ID " + id + " not found!"));
	}
	
	public InternshipResponseDTO createInternship(InternshipRequestDTO internship) {
		Internship entity = internshipMapper.toEntity(internship);
		
		entity.setCompany(
				companyRepository.findById(internship.getCompanyId())
				.orElseThrow(() -> new EntityNotFoundException("Company with ID " + internship.getCompanyId() + " not found!")));
		
		List<Technology> technologies = new ArrayList<>();
		for(Integer technologyId : internship.getTechnologyIds()) {
			technologies.add(
					technologyRepository.findById(technologyId)
					.orElseThrow(() -> new EntityNotFoundException("Technology with ID " + technologyId + " not found!")));
		}
		entity.setTechnologies(technologies);
		
		return internshipMapper.toResponseDTO(internshipRepository.save(entity));
	}
	
	public InternshipResponseDTO updateInternship(Integer id, InternshipRequestDTO internship) {
		Internship entity = internshipRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Internship with ID " + id + " not found!"));
		
		entity.setName(internship.getName());
		entity.setDescription(internship.getDescription());
		entity.setStartDate(internship.getStartDate());
		entity.setEndDate(internship.getEndDate());
		entity.setTermsAndConditions(internship.getTermsAndConditions());
		
		
		List<Technology> technologies = new ArrayList<>();
		for(Integer technologyId : internship.getTechnologyIds()) {
			technologies.add(
					technologyRepository.findById(technologyId)
					.orElseThrow(() -> new EntityNotFoundException("Technology with ID " + technologyId + " not found!")));
		}
		
		entity.getTechnologies().clear();
		entity.getTechnologies().addAll(technologies);
		
		return internshipMapper.toResponseDTO(internshipRepository.save(entity));
	}
	
	public void deleteInternship(Integer id) {
		Internship entity = internshipRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Internship with ID " + id + " not found!"));
		
		internshipRepository.delete(entity);
	}
	
	public String generateRssFeed() {
		SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle("InternHub - Available Internships");
        feed.setLink("http://localhost:8080/api/v1/internships/rss");
        feed.setDescription("The latest advertisements for student internships");

        List<Internship> internships = internshipRepository.findAll();
        List<SyndEntry> entries = new ArrayList<>();

        for (Internship internship : internships) {
            SyndEntry entry = new SyndEntryImpl();
            entry.setTitle(internship.getName());
            entry.setLink("http://localhost:8080/api/v1/internships/" + internship.getInternshipId());
            entry.setPublishedDate(new Date());

            SyndContent description = new SyndContentImpl();
            description.setType("text/plain");
            description.setValue(internship.getDescription());
            entry.setDescription(description);

            entries.add(entry);
        }

        feed.setEntries(entries);

        try {
            return new SyndFeedOutput().outputString(feed);
        } catch (FeedException e) {
            throw new RuntimeException("Error generating RSS feed", e);
        }
    }
	
	
	//TODO AI recommendations
	

}
