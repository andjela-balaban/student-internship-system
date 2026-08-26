package org.unibl.etf.internship.intern_hub.service;

import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.repository.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void changePassword(Integer userId, ChangePasswordRequestDTO request) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User with ID: " + userId + " not found"));
		
		if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword()))
			throw new IllegalArgumentException("Incorrect current password!");
		
		if(!request.getNewPassword().equals(request.getNewPasswordConfirm()))
			throw new IllegalArgumentException("New and old passwords don't match!");
		
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		
		userRepository.save(user);
	}
}
