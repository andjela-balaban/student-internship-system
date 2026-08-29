package org.unibl.etf.internship.intern_hub.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.service.*;
import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("${api.prefix}/internships/{internshipId}/applications")
public class ApplicationController {
	
	@Autowired
	private ApplicationService applicationService;
	
	@GetMapping
	public ResponseEntity<List<ApplicationResponseDTO>> getAllByInternshipId(@PathVariable Integer internshipId) {
		return ResponseEntity.ok(applicationService.getAllByInternshipId(internshipId));
	}
	
	@GetMapping("/{applicationId}")
	public ResponseEntity<ApplicationResponseDTO> getByInternshipIdAndApplicationId(@PathVariable Integer internshipId, @PathVariable Integer applicationId) {
		return ResponseEntity.ok(applicationService.getByInternshipIdAndApplicationId(internshipId, applicationId));
	}
	
	@PostMapping
    public ResponseEntity<ApplicationResponseDTO> createApplication(@PathVariable Integer internshipId, @Valid @RequestBody ApplicationRequestDTO request) {
        ApplicationResponseDTO createdApplication = applicationService.createApplication(internshipId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApplication);
    }
	
	@PutMapping("/{applicationId}")
    public ResponseEntity<ApplicationResponseDTO> updateApplicationStatus(@PathVariable Integer internshipId, @PathVariable Integer applicationId, @Valid @RequestBody ApplicationStatusUpdateDTO request) {
        return ResponseEntity.ok(applicationService.updateApplication(internshipId, applicationId, request));
    }
	
	@DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Integer internshipId, @PathVariable Integer applicationId) {
        applicationService.deleteApplication(internshipId, applicationId);
        return ResponseEntity.noContent().build();
    }

}
