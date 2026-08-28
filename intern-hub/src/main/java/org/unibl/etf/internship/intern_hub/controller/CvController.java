package org.unibl.etf.internship.intern_hub.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.service.*;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("${api.prefix}/cvs")
public class CvController {

	@Autowired
	private CvService cvService;
	
	@GetMapping
	public ResponseEntity<List<CvResponseDTO>> getAll() {
		return ResponseEntity.ok(cvService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CvResponseDTO> getById(@PathVariable Integer id) {
		return ResponseEntity.ok(cvService.getById(id));
	}
	
	@GetMapping("/student/{studentId}/image")
	public ResponseEntity<byte[]> getCvImage(@PathVariable Integer studentId) {
		byte[] imageBytes = cvService.getCvImage(studentId);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
	}
	
	@GetMapping("/student/{studentId}")
	public ResponseEntity<CvResponseDTO> getByStudentId(@PathVariable Integer studentId) {
		return ResponseEntity.ok(cvService.getCvByStudentId(studentId));
	}
	
	@PostMapping(value = "/student/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<CvResponseDTO> createCv(
			@PathVariable Integer studentId,
            @RequestPart("cv") @Valid CvRequestDTO cv,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {

		CvResponseDTO createdCv = cvService.createCv(studentId, cv, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCv);
	}

	
	@PutMapping(value = "/student/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CvResponseDTO> updateCv(
            @PathVariable Integer studentId,
            @RequestPart("cv") @Valid CvRequestDTO cv,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {

        return ResponseEntity.ok(cvService.updateCv(studentId, cv, imageFile));
    }
	
	@DeleteMapping("/student/{studentId}")
    public ResponseEntity<Void> deleteCv(@PathVariable Integer studentId) {
        cvService.deleteCv(studentId);
        return ResponseEntity.noContent().build();
    }	
}
