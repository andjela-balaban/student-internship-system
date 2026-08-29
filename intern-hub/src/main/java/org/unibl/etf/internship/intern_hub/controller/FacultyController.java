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
@RequestMapping("${api.prefix}/faculties")
public class FacultyController {

	@Autowired
	private FacultyService facultyService;
	
	@GetMapping
	public ResponseEntity<List<FacultyResponseDTO>> getAll() {
		return ResponseEntity.ok(facultyService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FacultyResponseDTO> getById(@PathVariable Integer id) {
		return ResponseEntity.ok(facultyService.getById(id));
	}
	
	@PostMapping
	public ResponseEntity<FacultyResponseDTO> createFaculty(@Valid @RequestBody FacultyRequestDTO request) {
		FacultyResponseDTO createdFaculty = facultyService.createFaculty(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdFaculty);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<FacultyResponseDTO> updateFaculty(@PathVariable Integer id, @Valid @RequestBody FacultyRequestDTO requst) {
		return ResponseEntity.ok(facultyService.updateFaculty(id, requst));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFaculty(@PathVariable Integer id) {
		facultyService.deleteFaculty(id);
		return ResponseEntity.noContent().build();
	}
}
