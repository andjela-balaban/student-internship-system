package org.unibl.etf.internship.intern_hub.repository;

import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.unibl.etf.internship.intern_hub.model.entity.*;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
	
}
