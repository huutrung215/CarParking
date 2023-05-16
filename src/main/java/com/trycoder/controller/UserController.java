package com.trycoder.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trycoder.model.UserDtls;
import com.trycoder.repository.UserRepository;
import com.trycoder.service.UserService;
import com.trycoder.dto.UserDto;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	UserService userService;

	@ModelAttribute
	private void userDetails(Model m, Principal p) {
		String email = p.getName();
		UserDtls user = userRepo.findByEmail(email);

		m.addAttribute("user", user);
	}
	
	@GetMapping("/Users")
	public String userManagerment(Model model) {
		List<UserDtls> users = userRepo.findByRole("ROLE_USER");
		List<UserDto> userDto = new ArrayList<>();
		model.addAttribute("pageTitle", "Bảng thông tin người dùng");
		for (UserDtls user : users) {
			UserDto userModel = new UserDto();
			userModel.setId(user.getId());
			userModel.setFullName(user.getFullName());
			userModel.setEmail(user.getEmail());
			userModel.setAddress(user.getAddress());
			userModel.setDob(user.getDob());
			userModel.setGender(user.getGender());
			userModel.setPhone(user.getPhone());
			userModel.setCreatedUser(user.getCreatedUser());
			userModel.setPassword(user.getPassword());
			userDto.add(userModel);
	    }
	    model.addAttribute("users", userDto);
		return "userManagerment";
	}
	
	@GetMapping("/Users/edit/{id}")
	public String EditUserForm(@PathVariable("id") Long id, Model model) {
		try {
			UserDtls userId = userService.getUserById(id);
			model.addAttribute("userId", userId);
			model.addAttribute("pageTitle", "Cập nhật thông tin người dùng");
			return "editUser";
			
		} catch (ConfigDataResourceNotFoundException ex) {
            // Handle exception if position is not found
            return "redirect:/index";
        }
	}
	
	@PostMapping("/UpdateUser/{id}")
	public String EditUser(@PathVariable("id") Long id, UserDtls newUserDtls,
	        BindingResult result, RedirectAttributes ra) {
		if (result.hasErrors()) {
            return "Users";
        }   	
    	 try {
    		 UserDtls user = userService.updateUserDtls(id, newUserDtls);
    		 ra.addFlashAttribute("msg", "người dùng cập nhật thành công");
    		 
    	 }catch (ConfigDataResourceNotFoundException ex) {
 	    }
     	return "redirect:/Users/edit/{id}";
	}
	
	@GetMapping("/DencenRole")
	public String dencenRole(Model model) {
		List<UserDtls> users = userRepo.findByRole("ROLE_USER");
		List<UserDto> userDto = new ArrayList<>();
		model.addAttribute("pageTitle", "Phân quyền");
		for (UserDtls user : users) {
			UserDto userModel = new UserDto();
			userModel.setId(user.getId());
			userModel.setFullName(user.getFullName());
			userModel.setEmail(user.getEmail());
			userModel.setAddress(user.getAddress());
			userModel.setDob(user.getDob());
			userModel.setGender(user.getGender());
			userModel.setPhone(user.getPhone());
			userModel.setPassword(user.getPassword());
			userDto.add(userModel);
	    }
	    model.addAttribute("users", userDto);
		return "dencenRole";
	}
}
