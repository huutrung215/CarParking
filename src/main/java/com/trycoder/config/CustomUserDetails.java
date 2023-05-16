package com.trycoder.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.trycoder.model.UserDtls;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

//sử dụng để cung cấp thông tin người dùng cho Spring Security trong quá trình xác thực và phân quyền
public class CustomUserDetails implements UserDetails {

	private UserDtls user;

	public CustomUserDetails(UserDtls user) {
		super();
		this.user = user;
	}
	
	// đối tượng SimpleGrantedAuthority được tạo ra từ vai trò (role) của người dùng được trả về
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());
		return Arrays.asList(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() { //trả về mật khẩu của người dùng
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() { //trả về tên đăng nhập của người dùng
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	//Phương thức isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), và isEnabled() đều trả về true, 
	//cho thấy tài khoản của người dùng không bị hết hạn, không bị khóa, không bị mật khẩu hết hạn và tài khoản đang được bật

}
