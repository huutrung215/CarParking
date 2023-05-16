package com.trycoder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trycoder.model.UserDtls;
import com.trycoder.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	public UserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
	
	//sử dụng để tìm kiếm một người dùng trong cơ sở dữ liệu theo tên người dùng 
	//(trong trường hợp này là địa chỉ email) và trả về một đối tượng UserDetails để sử dụng cho xác thực
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserDtls user = userRepo.findByEmail(email);

		if (user != null) {
			return new CustomUserDetails(user); //người dùng được tìm thấy
		}

		throw new UsernameNotFoundException("user not available");
	}
}
