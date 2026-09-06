package org.unibl.etf.internship.intern_hub.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.service.*;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("${api.prefix}/internships")
public class InternshipController {

	@Autowired
	private InternshipService internshipService;
	
	@GetMapping
	public ResponseEntity<List<InternshipResponseDTO>> getAll() {
		return ResponseEntity.ok(internshipService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<InternshipResponseDTO> getById(@PathVariable Integer id) {
		return ResponseEntity.ok(internshipService.getById(id));
	}
	
	@PostMapping
	public ResponseEntity<InternshipResponseDTO> createInternship(@Valid @RequestBody InternshipRequestDTO request) {
		InternshipResponseDTO createdInternship = internshipService.createInternship(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdInternship);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<InternshipResponseDTO> updateInternship(@PathVariable Integer id, @Valid @RequestBody InternshipRequestDTO request) {
		return ResponseEntity.ok(internshipService.updateInternship(id, request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteInternship(@PathVariable Integer id) {
		internshipService.deleteInternship(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/rss", produces = "application/rss+xml; charset=UTF-8")
    public ResponseEntity<String> getRssFeed() {
        String rssXml = internshipService.generateRssFeed();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/rss+xml; charset=UTF-8"))
                .body(rssXml);
    }
	
	@GetMapping("/ai-recommendations/{studentId}")
	public ResponseEntity<List<AiInternshipRecommendationResponseDTO>> getRecommendations(@PathVariable Integer studentId) {
		return ResponseEntity.ok(internshipService.getRecommendations(studentId));
	}
}
