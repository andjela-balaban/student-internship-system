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
@RequestMapping("${api.prefix}/technologies")
public class TechnologyController {

	@Autowired
	private TechnologyService technologyService;
	
	@GetMapping
	public ResponseEntity<List<TechnologyResponseDTO>> getAll() {
		return ResponseEntity.ok(technologyService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TechnologyResponseDTO> getById(@PathVariable Integer id) {
		return ResponseEntity.ok(technologyService.getById(id));
	}
	
	@PostMapping
	public ResponseEntity<TechnologyResponseDTO> createTechnology(@Valid @RequestBody TechnologyRequestDTO request) {
		TechnologyResponseDTO createdTechnology = technologyService.createTechnology(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTechnology);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TechnologyResponseDTO> updateTechnology(@PathVariable Integer id, @Valid @RequestBody TechnologyRequestDTO request) {
		return ResponseEntity.ok(technologyService.updateTechnology(id, request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTechnology(@PathVariable Integer id) {
		technologyService.deleteTechnology(id);
		return ResponseEntity.noContent().build();
	}
	
}
