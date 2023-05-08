package com.trycoder.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trycoder.model.UserDtls;

@Repository
public interface UserRepository extends JpaRepository<UserDtls, Long> {
	
	Long countByRoleAndCreatedUserBetween(String role , LocalDateTime start, LocalDateTime end);

	public boolean existsByEmail(String email);

	public UserDtls findByEmail(String email);

	List<UserDtls> findByRole(String string);
	
}
