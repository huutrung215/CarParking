package com.trycoder.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDto {
	private Long id;
	private String fullName;
	private String email;
	private String address;
	private LocalDate dob;
	private Boolean gender;
	private String phone;
	private String password;
	private LocalDateTime createdUser;
	private String role;
	
	public UserDto() {}
	
	public UserDto(UserDto userDto) {
		this.id = userDto.getId();
		this.fullName = userDto.getFullName();
		this.email = userDto.getEmail();
		this.address = userDto.getAddress();
		this.dob = userDto.getDob();
		this.gender = userDto.getGender();
		this.phone = userDto.getPhone();
		this.password = userDto.getPassword();
		this.createdUser = userDto.createdUser;
		this.role = userDto.getRole();
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDateTime getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(LocalDateTime createdUser) {
		this.createdUser = createdUser;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
