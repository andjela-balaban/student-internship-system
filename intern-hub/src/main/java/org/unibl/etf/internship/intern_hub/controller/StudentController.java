package org.unibl.etf.internship.intern_hub.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.service.*;
import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("${api.prefix}/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping
	public ResponseEntity<List<StudentResponseDTO>> getAll() {
		return ResponseEntity.ok(studentService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StudentResponseDTO> getById(@PathVariable Integer id) {
		return ResponseEntity.ok(studentService.getById(id));
	}
	
	@GetMapping("/search")
    public ResponseEntity<List<StudentResponseDTO>> getAllByFullName(@RequestParam String fullName) {
        return ResponseEntity.ok(studentService.getAllByFullName(fullName));
    }
	
	@PostMapping
	public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO request) {
		StudentResponseDTO createdStudent = studentService.createStudent(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Integer id, @Valid @RequestBody StudentRequestDTO request) {
		return ResponseEntity.ok(studentService.updateStudent(id, request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
		studentService.deleteStudent(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}/applications")
	public ResponseEntity<List<ApplicationResponseDTO>> getStudentApplications(@PathVariable Integer id) {
		return ResponseEntity.ok(studentService.getStudentApplications(id));
	}
	
	@PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> createStudentsFromCsv(@RequestParam("facultyId") Integer facultyId, @RequestParam("file") MultipartFile file) throws Exception {
	    studentService.createFromCSV(file, facultyId);
	    return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
