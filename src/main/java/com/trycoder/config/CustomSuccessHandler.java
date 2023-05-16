package com.trycoder.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//Code trên định nghĩa một custom AuthenticationSuccessHandler để xử lý khi authentication thành công (login thành công).
		//Trong phương thức onAuthenticationSuccess(), đầu vào là các tham số HttpServletRequest, HttpServletResponse và Authentication.

		//Trong phương thức, đầu tiên chúng ta lấy danh sách các vai trò được cấp cho người dùng thông qua đoạn code:
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

		//Tiếp theo, chúng ta kiểm tra xem nếu vai trò của người dùng là "ROLE_ADMIN" thì sẽ chuyển hướng về trang chủ ("/"), 
		//ngược lại nếu vai trò của người dùng là bất kỳ thứ gì khác, thì sẽ chuyển hướng về trang "/user/".
		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("/");
		} else {
			response.sendRedirect("/user/");
		}
		//Từ đó, CustomSuccessHandler sẽ định tuyến người dùng đến trang chủ hoặc trang người dùng tương ứng sau khi đã xác thực thành công.
	}
}

