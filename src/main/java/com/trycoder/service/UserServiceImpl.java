package com.trycoder.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.trycoder.model.Position;
import com.trycoder.model.UserDtls;
import com.trycoder.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	@Override
	public UserDtls createUser(String password, String reapeatPassord, UserDtls user) {
		if(password.equals(reapeatPassord)) {
			user.setPassword(passwordEncode.encode(user.getPassword()));
			user.setRole("ROLE_USER");
			user.setCreatedUser(LocalDateTime.now());
			return userRepo.save(user);
		}
		return null;
	}

	@Override
	public boolean checkEmail(String email) {
		return userRepo.existsByEmail(email);
	} 
	
	@Override
	 public UserDtls getUserById(Long id) {
	        return userRepo.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy user id " + id));
	} 
	
	@Override
	 public UserDtls updateUserDtls(Long id, UserDtls newUser) {
		UserDtls user = getUserById(id);
		user.setEmail(newUser.getEmail());
		user.setFullName(newUser.getFullName());
		user.setAddress(newUser.getAddress());
		user.setDob(newUser.getDob());
		user.setGender(newUser.getGender());
		user.setPhone(newUser.getPhone());
		user.setCreatedUser(newUser.getCreatedUser());
	    return userRepo.save(user);
	} 
	
	@Override
	@Transactional
    public void promoteUserToAdmin(Long userId) {
		UserDtls user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + userId));
        user.setRole("ROLE_ADMIN");
        userRepo.save(user);
    }
	
	@Override
	@Transactional
    public void promoteUserToStaff(Long userId) {
		UserDtls user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + userId));
        user.setRole("ROLE_STAFF");
        userRepo.save(user);
    }
	
}
