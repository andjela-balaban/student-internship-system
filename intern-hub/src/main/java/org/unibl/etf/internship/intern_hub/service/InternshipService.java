package org.unibl.etf.internship.intern_hub.service;

import org.unibl.etf.internship.intern_hub.mapper.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.repository.*;
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
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CvService cvService;
	
	@Autowired
	private AiService groqService;
	
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
	
	public List<AiInternshipRecommendationResponseDTO> getRecommendations(Integer studentId) {
		StudentResponseDTO student = studentService.getById(studentId);
		CvResponseDTO cv = null;
		try {
			cv = cvService.getCvByStudentId(studentId);
		} catch (Exception e) {
		}
		
		List<InternshipResponseDTO> internships = getAll();
		if (internships == null || internships.isEmpty()) {
			return Collections.emptyList();
		}
		
		AiRequestDTO request = new AiRequestDTO(internships, student, cv);
		List<AiRecommendationResultDTO> results = groqService.getInternshipIds(request);
		
		List<AiInternshipRecommendationResponseDTO> recommendations = new ArrayList<>();
		if (results != null && !results.isEmpty()) {
			for (AiRecommendationResultDTO r : results) {
				try {
					InternshipResponseDTO internship = getById(r.getInternshipId());
					recommendations.add(new AiInternshipRecommendationResponseDTO(
						internship.getInternshipId(),
						internship.getName(), 
						internship.getDescription(),
						internship.getCompany() != null ? internship.getCompany().getName() : "",
						internship.getStartDate(),
						internship.getTechnologies(),
						r.getMatchPercentage(),
						r.getReason()
					));
				} catch (Exception e) {
				}
			}
		}
		
		if (recommendations.isEmpty()) {
			Set<String> studentTechNames = new HashSet<>();
			if (cv != null && cv.getTechnologies() != null) {
				for (TechnologyResponseDTO tech : cv.getTechnologies()) {
					if (tech.getName() != null) {
						studentTechNames.add(tech.getName().toLowerCase());
					}
				}
			}

			for (InternshipResponseDTO internship : internships) {
				List<String> matchedTechs = new ArrayList<>();
				if (internship.getTechnologies() != null) {
					for (TechnologyResponseDTO tech : internship.getTechnologies()) {
						if (tech.getName() != null && studentTechNames.contains(tech.getName().toLowerCase())) {
							matchedTechs.add(tech.getName());
						}
					}
				}

				double score = 0.65;
				String reason = "Matches your faculty background: " + (student.getFaculty() != null ? student.getFaculty().getName() : "IT");

				if (!studentTechNames.isEmpty() && internship.getTechnologies() != null && !internship.getTechnologies().isEmpty()) {
					double matchRatio = (double) matchedTechs.size() / internship.getTechnologies().size();
					if (matchedTechs.size() > 0) {
						score = Math.min(0.98, 0.70 + (matchRatio * 0.28));
						reason = "Matches " + matchedTechs.size() + " skills from your CV: " + String.join(", ", matchedTechs);
					} else {
						score = 0.50;
						reason = "General IT internship matching your profile.";
					}
				}

				recommendations.add(new AiInternshipRecommendationResponseDTO(
					internship.getInternshipId(),
					internship.getName(),
					internship.getDescription(),
					internship.getCompany() != null ? internship.getCompany().getName() : "",
					internship.getStartDate(),
					internship.getTechnologies(),
					Math.round(score * 100.0) / 100.0,
					reason
				));
			}
		}
		
		recommendations.sort((r1, r2) -> Double.compare(r2.getMatchPercentage(), r1.getMatchPercentage()));
		
		return recommendations;
	}

}
