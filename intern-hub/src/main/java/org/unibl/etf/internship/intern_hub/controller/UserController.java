package org.unibl.etf.internship.intern_hub.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.service.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PutMapping("/{id}/change-password")
	public ResponseEntity<Void> changePassword(@PathVariable Integer id, @Valid @RequestBody ChangePasswordRequestDTO request) {
		service.changePassword(id, request);
		
		return ResponseEntity.ok().build();
	}
}
