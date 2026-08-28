package org.unibl.etf.internship.intern_hub.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.dto.filter.*;
import org.unibl.etf.internship.intern_hub.service.*;
import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("${api.prefix}/internship-logs")
public class InternshipLogController {
	
	@Autowired
    private InternshipLogService logService;

    @GetMapping
    public ResponseEntity<List<InternshipLogResponseDTO>> getAll(InternshipLogFilterDTO filter) {
        return ResponseEntity.ok(logService.getAll(filter));
    }

    @GetMapping("/{logId}")
    public ResponseEntity<InternshipLogResponseDTO> getById(@PathVariable Integer logId) {
        return ResponseEntity.ok(logService.getById(logId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<InternshipLogResponseDTO>> getAllLogsForStudent(@PathVariable Integer studentId) {
        return ResponseEntity.ok(logService.getAllLogsForStudent(studentId));
    }

    @GetMapping("/internship/{internshipId}")
    public ResponseEntity<List<InternshipLogResponseDTO>> getAllLogsForInternship(@PathVariable Integer internshipId) {
        return ResponseEntity.ok(logService.getAllLogsForInternship(internshipId));
    }

    @PutMapping("/{logId}/student-content")
    public ResponseEntity<Void> updateStudentLogContent(@PathVariable Integer logId, @Valid @RequestBody StudentLogUpdateDTO dto) {
        logService.updateStudentLogContent(logId, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{logId}/faculty-review")
    public ResponseEntity<Void> reviewLogByFaculty(@PathVariable Integer logId, @Valid @RequestBody FacultyReviewLogDTO dto) {
        logService.reviewLogByFaculty(logId, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{logId}/company-review")
    public ResponseEntity<Void> reviewLogByCompany(@PathVariable Integer logId, @Valid @RequestBody CompanyReviewLogDTO dto) {
        logService.reviewLogByCompany(logId, dto);
        return ResponseEntity.ok().build();
    }
}
