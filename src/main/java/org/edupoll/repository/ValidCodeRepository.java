package org.edupoll.repository;

import org.edupoll.model.entity.ValidCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidCodeRepository extends JpaRepository<ValidCode, Long> {
	
	ValidCode findByEmail(String email);
}
