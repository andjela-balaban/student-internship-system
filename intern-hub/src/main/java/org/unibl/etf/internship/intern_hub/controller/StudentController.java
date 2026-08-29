package org.unibl.etf.internship.intern_hub.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.service.*;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("${api.prefix}/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
    private PdfService pdfService;
	
	@Autowired
    private CvService cvService;
	
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
	
	@GetMapping(value = "/{studentId}/cv/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> downloadCvPdfByStudentId(@PathVariable Integer studentId) throws Exception {
	    CvResponseDTO cvDto = cvService.getCvByStudentId(studentId);
	    byte[] profileImage = cvService.getCvImage(studentId);

	    byte[] pdfBytes = pdfService.generateCvPdf(cvDto, profileImage);

	    String fileName = "CV_" + cvDto.getStudent().getFirstName() + "_" + cvDto.getStudent().getLastName() + ".pdf";

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    headers.setContentDisposition(ContentDisposition.inline()
	            .filename(fileName)
	            .build());

	    return ResponseEntity.ok().headers(headers).body(pdfBytes);
	}
	
	@PostMapping(value = "/{studentId}/cv-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> uploadCvImage(
	        @PathVariable Integer studentId,
	        @RequestParam("image") MultipartFile image) throws IOException {

	    cvService.uploadCvImage(studentId, image);
	    return ResponseEntity.ok().build();
	}

}
