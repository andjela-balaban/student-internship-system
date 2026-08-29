package org.unibl.etf.internship.intern_hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.internship.intern_hub.model.dto.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;
import org.unibl.etf.internship.intern_hub.repository.UserRepository;
import org.unibl.etf.internship.intern_hub.util.JwtService;

@Service
@Transactional
public class AuthService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private JwtService jwtService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
    
    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials!");
        }

        String role = determineRole(user);
        
        String jwtToken = jwtService.generateToken(user.getUserId(), user.getUsername(), role);

        return new LoginResponseDTO(
                jwtToken,
                user.getUserId(),
                role,
                user.getUsername()
        );
    }
    
    private String determineRole(User user) {
    	if (user instanceof Student) {
            return "ROLE_STUDENT";
        } else if (user instanceof Company) {
            return "ROLE_COMPANY";
        } else if (user instanceof Faculty) {
            return "ROLE_FACULTY";
        } else {
            throw new IllegalStateException("User type is unknown for user: " + user.getUsername());
        }
    }
}
