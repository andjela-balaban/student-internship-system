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
@RequestMapping("${api.prefix}/companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@GetMapping
	public ResponseEntity<List<CompanyResponseDTO>> getAll() {
		return ResponseEntity.ok(companyService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CompanyResponseDTO> getById(@PathVariable Integer id) {
		return ResponseEntity.ok(companyService.getById(id));
	}
	
	@PostMapping
	public ResponseEntity<CompanyResponseDTO> createCompany(@Valid @RequestBody CompanyRequestDTO request) {
		CompanyResponseDTO createdCompany = companyService.createCompany(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CompanyResponseDTO> updateCompany(@PathVariable Integer id, @Valid @RequestBody CompanyRequestDTO request) {
		return ResponseEntity.ok(companyService.updateCompany(id, request));
	}
	
	@PutMapping("{id}/status")
	public ResponseEntity<CompanyResponseDTO> updateStatus(@PathVariable Integer id, @Valid @RequestBody CompanyStatusUpdateDTO newStatus) {
		return ResponseEntity.ok(companyService.updateStatus(id, newStatus));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCompany(@PathVariable Integer id) {
		companyService.deleteCompany(id);
		return ResponseEntity.noContent().build();
	}
}
