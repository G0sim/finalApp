package org.edupoll.repository;

import java.util.Optional;

import org.edupoll.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

	public boolean existsByEmail(String email);
	public Optional<User> findByEmail(String email);
}


/*
리턴타입 : 
one or nothing (있거나 없거나)
	T / Optional<T>
	
many (여러개)
	List<T> / String<T> -> 처음부터 stream을 적용해서 리턴 가능
*/